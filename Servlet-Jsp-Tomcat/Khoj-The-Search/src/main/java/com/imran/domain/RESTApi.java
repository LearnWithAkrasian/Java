package com.imran.domain;

import com.imran.dto.PayloadDTO;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Vector;

// This class retrieves data from RestApiDTO
// and passes it to the NumberList class for searching.
public class RESTApi {
    private Long userId;
    private LocalDateTime startTime;
    private LocalDateTime endingTime;
    private Vector<PayloadDTO> payloads;

    public RESTApi() {

    }

    public Vector<PayloadDTO> getPayloads() {
        return payloads;
    }

    public void setPayloads(Vector<PayloadDTO> payloads) {
        this.payloads = payloads;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndingTime() {
        return endingTime;
    }

    public void setEndingTime(LocalDateTime endingTime) {
        this.endingTime = endingTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
