package com.imran.domain;


// This class is used to store data in 'customer' table.
public class User extends Domain {
    private String username;
    private String password;
    public User(){}

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
}
