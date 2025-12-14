package com.virendra.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.virendra.dto.LoginRequest;
import com.virendra.dto.RegisterRequest;
import com.virendra.model.User;
import com.virendra.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
     @Autowired
     UserRepository userRepository;

    // REGISTER
    public String register(RegisterRequest req) {

        if (userRepository.existsByEmail(req.getEmail())) {
            return "Email already exists";
        }

        User user = new User();
        user.setEmail(req.getEmail());
        user.setPassword(req.getPassword()); // plain
        user.setUsername(req.getUsername());

        userRepository.save(user);
        return "User registered successfully";
    }

    // LOGIN
    public String login(LoginRequest req) {

        Optional<User> opt = userRepository.findByEmail(req.getEmail());

        if (opt.isEmpty()) {
            return "Invalid email";
        }

        User user = opt.get();

        if (!user.getPassword().equals(req.getPassword())) {
            return "Invalid password";
        }

        return "Login successful";
    }
}

