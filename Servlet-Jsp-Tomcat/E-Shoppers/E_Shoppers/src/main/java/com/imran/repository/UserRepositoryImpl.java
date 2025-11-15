package com.imran.repository;

import com.imran.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class UserRepositoryImpl implements UserRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryImpl.class);
    private static final Set<User> users = new CopyOnWriteArraySet<>();
    @Override
    public void save(User user) {
        users.add(user);
        LOGGER.info("User save successful");
    }
}
