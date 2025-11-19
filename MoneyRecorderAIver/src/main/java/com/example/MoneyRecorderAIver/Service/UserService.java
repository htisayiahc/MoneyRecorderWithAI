package com.example.MoneyRecorderAIver.Service;

import com.example.MoneyRecorderAIver.DTO.UserRequestDTO;
import com.example.MoneyRecorderAIver.DTO.UserResponseDTO;
import com.example.MoneyRecorderAIver.Entity.UserEntity;
import com.example.MoneyRecorderAIver.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String REDIS_KEY_ALL_USERS = "users:all";
    private static final String REDIS_KEY_USER_BY_ID = "users:%d";
    private static final long CACHE_EXPIRATION_HOURS = 24;

    @SuppressWarnings("unchecked")
    public List<UserResponseDTO> getAllUsers() {
        // Try to get from Redis first
        List<UserResponseDTO> cachedUsers = (List<UserResponseDTO>) redisTemplate.opsForValue().get(REDIS_KEY_ALL_USERS);

        if (cachedUsers != null) {
            return cachedUsers;
        }

        // If not found in Redis, fetch from database
        List<UserEntity> users = userRepository.findAll();
        List<UserResponseDTO> userResponseDTOs = users.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());

        // Save to Redis with expiration
        redisTemplate.opsForValue().set(REDIS_KEY_ALL_USERS, userResponseDTOs, CACHE_EXPIRATION_HOURS, TimeUnit.HOURS);

        return userResponseDTOs;
    }

    public UserResponseDTO getUserById(Long userId) {
        // Try to get from Redis first
        String redisKey = String.format(REDIS_KEY_USER_BY_ID, userId);
        UserResponseDTO cachedUser = (UserResponseDTO) redisTemplate.opsForValue().get(redisKey);

        if (cachedUser != null) {
            return cachedUser;
        }

        // If not found in Redis, fetch from database
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        UserResponseDTO userResponseDTO = convertToResponseDTO(userEntity);

        // Save to Redis with expiration
        redisTemplate.opsForValue().set(redisKey, userResponseDTO, CACHE_EXPIRATION_HOURS, TimeUnit.HOURS);

        return userResponseDTO;
    }

    public void createUser(UserRequestDTO userRequestDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(userRequestDTO.getName());
        userEntity.setEmail(userRequestDTO.getEmail());
        userEntity.setPassword(userRequestDTO.getPassword());

        // Save to database
        UserEntity savedUser = userRepository.save(userEntity);

        // Save to Redis with key users:{userId}
        String redisKey = String.format(REDIS_KEY_USER_BY_ID, savedUser.getId());
        UserResponseDTO userResponseDTO = convertToResponseDTO(savedUser);
        redisTemplate.opsForValue().set(redisKey, userResponseDTO, CACHE_EXPIRATION_HOURS, TimeUnit.HOURS);

        // Invalidate the all users cache
        redisTemplate.delete(REDIS_KEY_ALL_USERS);
    }

    private UserResponseDTO convertToResponseDTO(UserEntity userEntity) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setUserId(userEntity.getId());
        dto.setName(userEntity.getName());
        dto.setEmail(userEntity.getEmail());
        dto.setCreatedAt(userEntity.getCreatedAt());
        dto.setUpdatedAt(userEntity.getUpdatedAt());
        return dto;
    }
}

