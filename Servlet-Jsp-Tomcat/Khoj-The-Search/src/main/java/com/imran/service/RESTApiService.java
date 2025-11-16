package com.imran.service;

import com.imran.dto.RestApiDTO;

import java.time.LocalDateTime;

public interface RESTApiService {
    void search(RestApiDTO restApiDTO);


    boolean isCorrectFormate(String dateTime);
}
