package com.example.MoneyRecorderAIver.Service;

import com.example.MoneyRecorderAIver.DTO.IncomeRequestDTO;
import com.example.MoneyRecorderAIver.DTO.IncomeResponseDTO;
import com.example.MoneyRecorderAIver.DTO.TotalIncomeRequestDTO;
import com.example.MoneyRecorderAIver.DTO.TotalIncomeResponseDTO;
import com.example.MoneyRecorderAIver.Entity.IncomeEntity;
import com.example.MoneyRecorderAIver.Entity.UserEntity;
import com.example.MoneyRecorderAIver.Repository.IncomeRepository;
import com.example.MoneyRecorderAIver.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class IncomeService {

    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String REDIS_KEY_ALL_INCOMES = "incomes:all";
    private static final String REDIS_KEY_INCOMES_BY_USER = "incomes:%d";
    private static final long CACHE_EXPIRATION_HOURS = 24;

    @SuppressWarnings("unchecked")
    public List<IncomeResponseDTO> getAllIncomes() {
        // Try to get from Redis first
        List<IncomeResponseDTO> cachedIncomes = (List<IncomeResponseDTO>) redisTemplate.opsForValue().get(REDIS_KEY_ALL_INCOMES);

        if (cachedIncomes != null) {
            return cachedIncomes;
        }

        // If not found in Redis, fetch from database
        List<IncomeEntity> incomes = incomeRepository.findAll();
        List<IncomeResponseDTO> incomeResponseDTOs = incomes.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());

        // Save to Redis with expiration
        redisTemplate.opsForValue().set(REDIS_KEY_ALL_INCOMES, incomeResponseDTOs, CACHE_EXPIRATION_HOURS, TimeUnit.HOURS);

        return incomeResponseDTOs;
    }

    @SuppressWarnings("unchecked")
    public List<IncomeResponseDTO> getIncomesByUserId(Long userId) {
        // Try to get from Redis first
        String redisKey = String.format(REDIS_KEY_INCOMES_BY_USER, userId);
        List<IncomeResponseDTO> cachedIncomes = (List<IncomeResponseDTO>) redisTemplate.opsForValue().get(redisKey);

        if (cachedIncomes != null) {
            return cachedIncomes;
        }

        // If not found in Redis, fetch from database
        List<IncomeEntity> incomes = incomeRepository.findByUserId(userId);
        List<IncomeResponseDTO> incomeResponseDTOs = incomes.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());

        // Save to Redis with expiration
        redisTemplate.opsForValue().set(redisKey, incomeResponseDTOs, CACHE_EXPIRATION_HOURS, TimeUnit.HOURS);

        return incomeResponseDTOs;
    }

    public void createIncomes(List<IncomeRequestDTO> incomeRequestDTOList) {
        for (IncomeRequestDTO incomeRequestDTO : incomeRequestDTOList) {
            createIncome(incomeRequestDTO);
        }

        // Invalidate cache for affected users
        if (!incomeRequestDTOList.isEmpty()) {
            // Get unique user IDs
            incomeRequestDTOList.stream()
                    .map(IncomeRequestDTO::getUserId)
                    .distinct()
                    .forEach(userId -> {
                        String redisKey = String.format(REDIS_KEY_INCOMES_BY_USER, userId);
                        redisTemplate.delete(redisKey);
                    });

            // Also invalidate the all incomes cache
            redisTemplate.delete(REDIS_KEY_ALL_INCOMES);
        }
    }

    private void createIncome(IncomeRequestDTO incomeRequestDTO) {
        // Verify user exists
        UserEntity userEntity = userRepository.findById(incomeRequestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + incomeRequestDTO.getUserId()));

        IncomeEntity incomeEntity = new IncomeEntity();
        incomeEntity.setUser(userEntity);
        incomeEntity.setAmount(incomeRequestDTO.getAmount());
        incomeEntity.setDescription(incomeRequestDTO.getDescription());
        incomeEntity.setIncomeDate(incomeRequestDTO.getIncomeDate());

        incomeRepository.save(incomeEntity);
    }

    public TotalIncomeResponseDTO getTotalIncome(TotalIncomeRequestDTO request) {
        // Validate request
        if (request.getUserId() == null || request.getStartDate() == null || request.getEndDate() == null) {
            throw new IllegalArgumentException("userId, startDate, and endDate are required");
        }

        // Get incomes from cache or database
        List<IncomeResponseDTO> incomes = getIncomesByUserId(request.getUserId());

        // Filter by date range and calculate total
        double totalIncome = incomes.stream()
                .filter(income -> {
                    Date incomeDate = income.getIncomeDate();
                    return !incomeDate.before(request.getStartDate()) && !incomeDate.after(request.getEndDate());
                })
                .mapToDouble(IncomeResponseDTO::getAmount)
                .sum();

        // Create response
        TotalIncomeResponseDTO response = new TotalIncomeResponseDTO();
        response.setStartDate(request.getStartDate());
        response.setEndDate(request.getEndDate());
        response.setTotalIncome(totalIncome);

        return response;
    }

    private IncomeResponseDTO convertToResponseDTO(IncomeEntity incomeEntity) {
        IncomeResponseDTO dto = new IncomeResponseDTO();
        dto.setIncomeId(incomeEntity.getId());
        dto.setUserId(incomeEntity.getUserId());
        dto.setUserName(incomeEntity.getUser().getName());
        dto.setAmount(incomeEntity.getAmount());
        dto.setDescription(incomeEntity.getDescription());
        dto.setIncomeDate(incomeEntity.getIncomeDate());
        return dto;
    }
}

