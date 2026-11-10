package com.example.tictactoe.service;

import com.example.tictactoe.model.User;
import com.example.tictactoe.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service

public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UUID signUp(String name, String email, String password) {
        if(userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists");
        }
        User newUser = new User(name, email, password);
        newUser = userRepository.save(newUser);
        return newUser.getId();
    }

    public UUID login(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()) {
            throw new IllegalArgumentException("Email doesn't exist, Pls sign in");
        }
        // user was an Optional<User>, now using this : user.get(), its an user.
        User currUser = user.get();
        if(password.equals(currUser.getPassword())) return currUser.getId();
        else throw new IllegalArgumentException("Your password is incorrect");
    }
}
