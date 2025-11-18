package com.example.MoneyRecorderAIver.Service;

import com.example.MoneyRecorderAIver.DTO.IncomeRequestDTO;
import com.example.MoneyRecorderAIver.DTO.IncomeResponseDTO;
import com.example.MoneyRecorderAIver.Entity.IncomeEntity;
import com.example.MoneyRecorderAIver.Entity.UserEntity;
import com.example.MoneyRecorderAIver.Repository.IncomeRepository;
import com.example.MoneyRecorderAIver.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IncomeService {

    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private UserRepository userRepository;

    public List<IncomeResponseDTO> getAllIncomes() {
        List<IncomeEntity> incomes = incomeRepository.findAll();
        return incomes.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public List<IncomeResponseDTO> getIncomesByUserId(Long userId) {
        List<IncomeEntity> incomes = incomeRepository.findByUserId(userId);
        return incomes.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public void createIncomes(List<IncomeRequestDTO> incomeRequestDTOList) {
        for (IncomeRequestDTO incomeRequestDTO : incomeRequestDTOList) {
            createIncome(incomeRequestDTO);
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

