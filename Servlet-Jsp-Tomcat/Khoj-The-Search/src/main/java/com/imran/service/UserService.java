package com.imran.service;

import com.imran.domain.User;
import com.imran.dto.LoginDTO;
import com.imran.dto.UserDTO;
import com.imran.exceptions.UserNotFoundException;

public interface UserService {
    void saveUser(UserDTO userDTO);
    boolean isNotUniqueUsername(UserDTO userDTO);

    User verifyUser(LoginDTO loginDTO) throws UserNotFoundException;

}
