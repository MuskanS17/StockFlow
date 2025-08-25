package com.ms.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ms.entity.User;
import com.ms.exception.UserAlreadyExistsException;
import com.ms.exception.UserNotFoundException;
import com.ms.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    public User createUser(User user) {
        Optional<User> existingUser = userRepo.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException("User with email '" + user.getEmail() + "' already exists");
        }
        
        
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        
        return userRepo.save(user);  
    }
    
    public void deleteUserById(Long id) {
        if (!userRepo.existsById(id)) {
            throw new UserNotFoundException("User with id " + id + " not found");
        }
        userRepo.deleteById(id);
    }
}
