package com.imran.service;

import com.imran.dto.NumberListDTO;

public interface NumberListService {
    void saveNumbers(NumberListDTO numberListDTO);

    boolean searchTheNumber(NumberListDTO numberListDTO);
}
