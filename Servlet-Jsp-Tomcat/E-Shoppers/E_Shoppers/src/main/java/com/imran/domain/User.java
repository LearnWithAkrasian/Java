package com.imran.domain;

import java.util.Objects;

public class User {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Two User objects are considered equal if their usernames are equal.
     * This is important for comparing users logically rather than by memory reference
     * for storing and retrieving objects from Map/Set.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    /**
     * Ensures two equal objects(based equals()) have the same hashcode
     * required for correct behavior in hash based collections like HashSet or HashMap
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(username);
    }
}
