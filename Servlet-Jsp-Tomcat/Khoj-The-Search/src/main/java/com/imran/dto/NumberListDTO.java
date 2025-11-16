package com.imran.dto;

import com.imran.domain.User;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

// This class retrieve values from home page
// like input values, search values and user id.
public class NumberListDTO {

    @NotEmpty
    private String inputValues;
    @NotEmpty
    private String searchValue;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getInputValues() {
        return inputValues;
    }

    public void setInputValues(String inputValues) {
        this.inputValues = inputValues;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

}
