package com.imran.domain;

import java.time.LocalDateTime;

// This object is used to store date and time when retrieving data from the database.
public class TimeAndDate {
    LocalDateTime time;
    Integer value;

    public TimeAndDate(LocalDateTime time, Integer value) {
        this.time = time;
        this.value = value;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
