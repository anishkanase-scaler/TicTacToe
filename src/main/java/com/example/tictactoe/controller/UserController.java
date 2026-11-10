package com.example.tictactoe.controller;

import com.example.tictactoe.dto.UserRequestDTO;
import com.example.tictactoe.model.User;
import com.example.tictactoe.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }


    //    @PostMapping("/signup")
    //    public ResponseEntity<UUID> signUp(UserRequestDTO userRequestDTO) {
    //        return ResponseEntity.status(HttpStatus.CREATED).body(userService.signUp(userRequestDTO.name(), userRequestDTO.email(), userRequestDTO.password()));
    //    }


    // RestController is Controller + Response Body, so we will return everything in form of JSON,
    // therefore return type is ResponseEntity<Entity>, which will convert Entity to JSON.
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(UserRequestDTO userRequestDTO) {
        try {
            // ResponseEntity<?>, we wrote ?, because in case if we get an exception from the service, then we won't be
            // able to return the UUID, in that case we will return a message.
            // We are returning the key value using Map.of().
            // status is used when we create something(201), and ok is used when we have to just return success(200).

            UUID token = userService.signUp(userRequestDTO.name(), userRequestDTO.email(), userRequestDTO.password());
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("token", token));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    //    @PostMapping("/login")
    //    public ResponseEntity<UUID> login(UserRequestDTO userRequestDTO) {
    //        return ResponseEntity.ok(userService.login(userRequestDTO.email(), userRequestDTO.password()));
    //    }

    @PostMapping("/login")
    public ResponseEntity<?> login(UserRequestDTO userRequestDTO) {
        try {
            UUID token = userService.login(userRequestDTO.email(), userRequestDTO.password());
            return ResponseEntity.ok(Map.of("token", token));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
