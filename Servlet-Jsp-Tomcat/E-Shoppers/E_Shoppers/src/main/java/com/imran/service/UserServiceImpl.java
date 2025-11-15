package com.imran.service;

import com.imran.domain.User;
import com.imran.dto.UserDto;
import com.imran.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private UserRepository userRepository;

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

        LOGGER.info("Passing the user to database successful");
        userRepository.save(user);
    }

    private String encryptPassword(String password) {
        return password;
    }
}
