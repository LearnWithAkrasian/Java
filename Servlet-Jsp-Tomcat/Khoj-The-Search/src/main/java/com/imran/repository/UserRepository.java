package com.imran.repository;

import com.imran.domain.User;

import java.util.Optional;

// This Interface is holding methods which is responsible to do the CRUD operations.
public interface UserRepository {
    void save(User user);
    Optional<User> findByUsername(String username);
}
