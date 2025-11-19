package com.example.MoneyRecorderAIver.Controller;

import com.example.MoneyRecorderAIver.DTO.IncomeRequestDTO;
import com.example.MoneyRecorderAIver.DTO.IncomeResponseDTO;
import com.example.MoneyRecorderAIver.DTO.TotalIncomeRequestDTO;
import com.example.MoneyRecorderAIver.DTO.TotalIncomeResponseDTO;
import com.example.MoneyRecorderAIver.DTO.UserIdRequestDTO;
import com.example.MoneyRecorderAIver.Service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/incomes")
public class IncomeController {

    @Autowired
    private IncomeService incomeService;

    @GetMapping("/all")
    public List<IncomeResponseDTO> getAllIncomes() {
        return incomeService.getAllIncomes();
    }

    @GetMapping("/byUserId")
    public List<IncomeResponseDTO> getIncomesByUserId(@RequestBody UserIdRequestDTO userIdRequestDTO) {
        return incomeService.getIncomesByUserId(userIdRequestDTO.getUserId());
    }

    @PostMapping("/create")
    public ResponseEntity<String> createIncome(@RequestBody List<IncomeRequestDTO> incomeRequestDTOList) {
        incomeService.createIncomes(incomeRequestDTOList);
        return ResponseEntity.ok("Income created successfully");
    }

    @GetMapping("/total")
    public TotalIncomeResponseDTO getTotalIncome(@RequestBody TotalIncomeRequestDTO totalIncomeRequestDTO) {
        return incomeService.getTotalIncome(totalIncomeRequestDTO);
    }
}

