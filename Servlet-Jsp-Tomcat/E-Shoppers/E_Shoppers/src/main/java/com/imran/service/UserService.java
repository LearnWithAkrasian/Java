package com.imran.service;

import com.imran.domain.User;
import com.imran.dto.UserDto;

import java.util.Optional;
import java.util.function.Supplier;

public interface UserService {
    void saveUser(UserDto userDto);
    boolean isNotUniqueUsername(UserDto userDto);
    boolean isNotUniqueEmail(UserDto userDto);
}
