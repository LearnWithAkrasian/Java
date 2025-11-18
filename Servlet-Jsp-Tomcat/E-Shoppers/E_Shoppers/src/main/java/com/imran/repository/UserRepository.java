package com.imran.repository;

import com.imran.domain.User;

import java.util.Optional;
import java.util.function.Supplier;

public interface UserRepository {
    void save(User user);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}
