package com.imran.service;

import com.imran.domain.User;
import com.imran.dto.LoginDTO;
import com.imran.dto.UserDTO;
import com.imran.exceptions.UserNotFoundException;
import com.imran.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserServiceImpl implements UserService{
    private static final Logger LOGGER
            = LoggerFactory.getLogger(UserServiceImpl.class);
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    // In this method we're building the logic that
    // how a new user will be stored in our database.
    @Override
    public void saveUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(encryptPassword(userDTO.getPassword()));

        userRepository.save(user);
    }

    @Override
    public boolean isNotUniqueUsername(UserDTO userDTO) {
        return userRepository
                .findByUsername(userDTO.getUsername())
                .isPresent();
    }


    @Override
    public User verifyUser(LoginDTO loginDTO)
            throws UserNotFoundException {
        var user
                = userRepository
                .findByUsername(loginDTO.getUsername())
                .orElseThrow(() -> new UserNotFoundException(
                        "User not found by " + loginDTO.getUsername()));
        var encrypted = encryptPassword(loginDTO.getPassword());
        if (user.getPassword().equals(encrypted)) {
            return user;
        } else {
            throw new UserNotFoundException("Incorrect password");
        }
    }

    private String encryptPassword(String password) {
        try {
            var digest = MessageDigest.getInstance("SHA-256");
            var bytes = digest.digest(
                    password.getBytes(StandardCharsets.UTF_8)
            );
            return bytesToHex(bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Unable to encrypt password", e);
        }
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
