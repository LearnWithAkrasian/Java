package com.imran.domain;

import java.time.LocalDateTime;
import java.util.List;

// This class is mapped to the 'user_values' table
// This class stores the logged-in user ID, input values, and the insertion time.
public class NumberList extends Domain {
    private List<Integer> numberList;
    private User user;
    private final LocalDateTime insertTime
            = LocalDateTime.now();


    public LocalDateTime getInsertTime() {
        return insertTime;
    }

    public NumberList() {}
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Integer> getNumberList() {
        return numberList;
    }

    public void setNumberList(List<Integer> numberList) {
        this.numberList = numberList;
    }
}
