package com.imran.repository;

import com.imran.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Supplier;

public class UserRepositoryImpl implements UserRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryImpl.class);

    // Thread-safe set to store User objects. Using CopyOnWriteArraySet ensures
    // that multiple threads can safely add users without explicit synchronization.
    // This is useful in a servlet/web environment where multiple requests may try
    // to save users simultaneously.
    private static final Set<User> users = new CopyOnWriteArraySet<>();

    /**
     * Saves a User object to the in-memory set.
     *
     * @param user The user to be saved.
     *
     * Reasoning:
     * - Using a Set ensures uniqueness: duplicate users will not be added.
     * - CopyOnWriteArraySet allows safe concurrent modifications.
     * - Logging info message after saving helps in debugging and tracking user activity.
     */
    @Override
    public void save(User user) {
        users.add(user);
        LOGGER.info("User save successful");
    }

    /**
     * Finds a user by their username.
     *
     * @param username The username to search for.
     * @return Optional<User> - contains the user if found, otherwise empty.
     *
     * Reasoning:
     * - Streams make it easy to filter the user set by username.
     * - Returning Optional<User> avoids returning null, promoting better null-safety.
     * - Using 'equals' ensures that the username matches exactly.
     */
    @Override
    public Optional<User> findByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return users.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }
}
