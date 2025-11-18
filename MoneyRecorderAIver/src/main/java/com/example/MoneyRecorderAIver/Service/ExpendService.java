package com.example.MoneyRecorderAIver.Service;

import com.example.MoneyRecorderAIver.DTO.ExpendRequestDTO;
import com.example.MoneyRecorderAIver.DTO.ExpendResponseDTO;
import com.example.MoneyRecorderAIver.Entity.ExpenseEntity;
import com.example.MoneyRecorderAIver.Entity.UserEntity;
import com.example.MoneyRecorderAIver.Repository.ExpenseRepository;
import com.example.MoneyRecorderAIver.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpendService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    public List<ExpendResponseDTO> getAllExpends() {
        List<ExpenseEntity> expends = expenseRepository.findAll();
        return expends.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public List<ExpendResponseDTO> getExpendsByUserId(Long userId) {
        List<ExpenseEntity> expends = expenseRepository.findByUserId(userId);
        return expends.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public void createExpends(List<ExpendRequestDTO> expendRequestDTOList) {
        for (ExpendRequestDTO expendRequestDTO : expendRequestDTOList) {
            createExpend(expendRequestDTO);
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

