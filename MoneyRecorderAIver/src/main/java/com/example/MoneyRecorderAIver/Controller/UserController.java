package com.example.MoneyRecorderAIver.Controller;

import com.example.MoneyRecorderAIver.DTO.UserIdRequestDTO;
import com.example.MoneyRecorderAIver.DTO.UserRequestDTO;
import com.example.MoneyRecorderAIver.DTO.UserResponseDTO;
import com.example.MoneyRecorderAIver.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<UserResponseDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/byUserId")
    public UserResponseDTO getUserById(@RequestBody UserIdRequestDTO userIdRequestDTO) {
        return userService.getUserById(userIdRequestDTO.getUserId());
    }

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody UserRequestDTO userRequestDTO) {
        userService.createUser(userRequestDTO);
        return ResponseEntity.ok("User created successfully");
    }
}

