package com.imran.dto;

import com.imran.annotation.PasswordEqual;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@PasswordEqual(
        first = "password",
        second = "confirmPassword",
        message = "password and confirm password do not match"
)
public class UserDto {

    @NotEmpty
    @Size(min = 3, max = 50)
    private String firstName;

    @NotEmpty
    @Size(min = 3, max = 50)
    private String lastName;

    @NotEmpty
    @Size(min = 3, max = 50)
    @Email
    private String email;

    @NotEmpty
    @Size(min = 3, max = 50)
    private String username;

    @NotEmpty
    @Size(min = 4, max = 16)
    private String password;

    @NotEmpty
    @Size(min = 4, max = 16)
    private String confirmPassword;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
