package com.example.MoneyRecorderAIver.Controller;

import com.example.MoneyRecorderAIver.DTO.ExpendRequestDTO;
import com.example.MoneyRecorderAIver.DTO.ExpendResponseDTO;
import com.example.MoneyRecorderAIver.DTO.UserIdRequestDTO;
import com.example.MoneyRecorderAIver.Service.ExpendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expends")
public class ExpendController {

    @Autowired
    private ExpendService expendService;

    @GetMapping("/all")
    public List<ExpendResponseDTO> getAllExpends() {
        return expendService.getAllExpends();
    }

    @GetMapping("/byUserId")
    public List<ExpendResponseDTO> getExpendsByUserId(@RequestBody UserIdRequestDTO userIdRequestDTO) {
        return expendService.getExpendsByUserId(userIdRequestDTO.getUserId());
    }

    @PostMapping("/create")
    public ResponseEntity<String> createExpend(@RequestBody List<ExpendRequestDTO> expendRequestDTOList) {
        expendService.createExpends(expendRequestDTOList);
        return ResponseEntity.ok("Expend created successfully");
    }
}

