package com.imran.dto;

import java.util.List;

// This class is employed to send a JSON object as a response to the client.
public class JsonDTO {
    private boolean status;
    private String user_id;
    private List<PayloadDTO> payloads;


    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public List<PayloadDTO> getPayload() {
        return payloads;
    }

    public void setPayload(List<PayloadDTO> payloads) {
        this.payloads = payloads;
    }
}

