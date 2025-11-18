package com.imran.service;

import com.imran.domain.User;
import com.imran.dto.UserDto;
import com.imran.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;


public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    // final ensures repository cannot be reassigned accidentally.
    private final UserRepository userRepository;

    // Constructor injection is safe and makes dependency mandatory.
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public void saveUser(UserDto userDto) {
        String encryptedPassword = encryptPassword(userDto.getPassword());
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(encryptedPassword);
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());

        LOGGER.info("User saved successfully to the database.");
        userRepository.save(user);
    }

    @Override
    public boolean isNotUniqueUsername(UserDto userDto) {
        return userRepository.findByUsername(userDto.getUsername())
                .isPresent();
    }

    @Override
    public boolean isNotUniqueEmail(UserDto userDto) {
        return userRepository.findByEmail(userDto.getEmail())
                .isPresent();
    }


    private String encryptPassword(String password) {
        try {
            var digest = MessageDigest.getInstance("SHA-256");
            var bytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            // The method converts bytes to hex.
            return HexFormat.of().formatHex(bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Unable to encrypt password.", e);
        }
    }
}
