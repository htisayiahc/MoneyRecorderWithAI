package com.example.MoneyRecorderAIver.Service;

import com.example.MoneyRecorderAIver.DTO.ExpendRequestDTO;
import com.example.MoneyRecorderAIver.DTO.ExpendResponseDTO;
import com.example.MoneyRecorderAIver.DTO.TotalExpendRequestDTO;
import com.example.MoneyRecorderAIver.DTO.TotalExpendResponseDTO;
import com.example.MoneyRecorderAIver.Entity.ExpenseEntity;
import com.example.MoneyRecorderAIver.Entity.UserEntity;
import com.example.MoneyRecorderAIver.Repository.ExpenseRepository;
import com.example.MoneyRecorderAIver.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class ExpendService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String REDIS_KEY_ALL_EXPENDS = "expends:all";
    private static final String REDIS_KEY_EXPENDS_BY_USER = "expends:%d";
    private static final long CACHE_EXPIRATION_HOURS = 24;

    @SuppressWarnings("unchecked")
    public List<ExpendResponseDTO> getAllExpends() {
        // Try to get from Redis first
        List<ExpendResponseDTO> cachedExpends = (List<ExpendResponseDTO>) redisTemplate.opsForValue().get(REDIS_KEY_ALL_EXPENDS);

        if (cachedExpends != null) {
            return cachedExpends;
        }

        // If not found in Redis, fetch from database
        List<ExpenseEntity> expends = expenseRepository.findAll();
        List<ExpendResponseDTO> expendResponseDTOs = expends.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());

        // Save to Redis with expiration
        redisTemplate.opsForValue().set(REDIS_KEY_ALL_EXPENDS, expendResponseDTOs, CACHE_EXPIRATION_HOURS, TimeUnit.HOURS);

        return expendResponseDTOs;
    }

    @SuppressWarnings("unchecked")
    public List<ExpendResponseDTO> getExpendsByUserId(Long userId) {
        // Try to get from Redis first
        String redisKey = String.format(REDIS_KEY_EXPENDS_BY_USER, userId);
        List<ExpendResponseDTO> cachedExpends = (List<ExpendResponseDTO>) redisTemplate.opsForValue().get(redisKey);

        if (cachedExpends != null) {
            return cachedExpends;
        }

        // If not found in Redis, fetch from database
        List<ExpenseEntity> expends = expenseRepository.findByUserId(userId);
        List<ExpendResponseDTO> expendResponseDTOs = expends.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());

        // Save to Redis with expiration
        redisTemplate.opsForValue().set(redisKey, expendResponseDTOs, CACHE_EXPIRATION_HOURS, TimeUnit.HOURS);

        return expendResponseDTOs;
    }

    public void createExpends(List<ExpendRequestDTO> expendRequestDTOList) {
        for (ExpendRequestDTO expendRequestDTO : expendRequestDTOList) {
            createExpend(expendRequestDTO);
        }

        // Invalidate cache for affected users
        if (!expendRequestDTOList.isEmpty()) {
            // Get unique user IDs
            expendRequestDTOList.stream()
                    .map(ExpendRequestDTO::getUserId)
                    .distinct()
                    .forEach(userId -> {
                        String redisKey = String.format(REDIS_KEY_EXPENDS_BY_USER, userId);
                        redisTemplate.delete(redisKey);
                    });

            // Also invalidate the all expends cache
            redisTemplate.delete(REDIS_KEY_ALL_EXPENDS);
        }
    }

    private void createExpend(ExpendRequestDTO expendRequestDTO) {
        // Verify user exists
        UserEntity userEntity = userRepository.findById(expendRequestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + expendRequestDTO.getUserId()));

        ExpenseEntity expenseEntity = new ExpenseEntity();
        expenseEntity.setUser(userEntity);
        expenseEntity.setAmount(expendRequestDTO.getAmount());
        expenseEntity.setDescription(expendRequestDTO.getDescription());
        expenseEntity.setExpendDate(expendRequestDTO.getExpendDate());

        expenseRepository.save(expenseEntity);
    }

    public TotalExpendResponseDTO getTotalExpend(TotalExpendRequestDTO request) {
        // Validate request
        if (request.getUserId() == null || request.getStartDate() == null || request.getEndDate() == null) {
            throw new IllegalArgumentException("userId, startDate, and endDate are required");
        }

        // Get expends from cache or database
        List<ExpendResponseDTO> expends = getExpendsByUserId(request.getUserId());

        // Filter by date range and calculate total
        double totalExpend = expends.stream()
                .filter(expend -> {
                    Date expendDate = expend.getExpendDate();
                    return !expendDate.before(request.getStartDate()) && !expendDate.after(request.getEndDate());
                })
                .mapToDouble(ExpendResponseDTO::getAmount)
                .sum();

        // Create response
        TotalExpendResponseDTO response = new TotalExpendResponseDTO();
        response.setStartDate(request.getStartDate());
        response.setEndDate(request.getEndDate());
        response.setTotalExpend(totalExpend);

        return response;
    }

    private ExpendResponseDTO convertToResponseDTO(ExpenseEntity expenseEntity) {
        ExpendResponseDTO dto = new ExpendResponseDTO();
        dto.setExpendId(expenseEntity.getId());
        dto.setUserId(expenseEntity.getUserId());
        dto.setUserName(expenseEntity.getUser().getName());
        dto.setAmount(expenseEntity.getAmount());
        dto.setDescription(expenseEntity.getDescription());
        dto.setExpendDate(expenseEntity.getExpendDate());
        return dto;
    }
}

