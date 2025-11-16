package com.imran.service;

import com.imran.domain.RESTApi;
import com.imran.domain.TimeAndDate;
import com.imran.dto.PayloadDTO;
import com.imran.dto.RestApiDTO;
import com.imran.repository.NumberListRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Vector;

public class RESTApiServiceImpl implements RESTApiService {

    NumberListRepository numberListRepository;

    public RESTApiServiceImpl(NumberListRepository numberListRepository) {
        this.numberListRepository = numberListRepository;
    }

    @Override
    public void search(RestApiDTO restApiDTO) {

        var restApi = copyToRestApi(restApiDTO);
        Vector <TimeAndDate> timeAndDates
                = numberListRepository.findByTimeAndUserId(restApi);

        restApiDTO.setStatus(!timeAndDates.isEmpty());
        restApiDTO.setPayloadDTOS(makePayloads(timeAndDates));
    }

    @Override
    public boolean isCorrectFormate(String dateTime) {
        String formatter = "yyyy-MM-dd HH:mm:ss";
        try {

            LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(formatter));
            return true;
        } catch (Exception e ) {
            return false;
        }
    }

    private RESTApi copyToRestApi(RestApiDTO restApiDTO) {
        var restApi = new RESTApi();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        restApi.setUserId(Long.parseLong(restApiDTO.getUserId()));
        restApi.setStartTime(LocalDateTime.parse(restApiDTO.getStartTime(), formatter));
        restApi.setEndingTime(LocalDateTime.parse(restApiDTO.getEndingTime(), formatter));

        return restApi;
    }

    private Vector<PayloadDTO> makePayloads(Vector<TimeAndDate> timesAndDates) {
        HashSet<LocalDateTime> times = new HashSet<>();
        Vector < Vector < Integer > > values = new Vector<>();

        // dividing time and insert_values
        for (int i = 0, j = -1; i < timesAndDates.size(); i++) {
            LocalDateTime time = timesAndDates.get(i).getTime();
            Integer val = timesAndDates.get(i).getValue();
            // if we found new time then we're inserting a vector in the values vector.
            if (!times.contains(time)) {
                j++;
                values.add(new Vector<>());
            }

            // adding time and values;
            times.add(time);
            values.get(j).add(val);
        }
        // dividing time and insert_values is complete


        Vector < PayloadDTO > payloadDTOS = new Vector<>();
        int j = 0;

        // Making payload objects' vector
        for (LocalDateTime time : times) {
            StringBuilder strValues = new StringBuilder();
            Vector < Integer > curValues = values.get(j++);

            for (int i = 0; i < curValues.size(); i++) {
                if (i == curValues.size()-1)
                    strValues.append(curValues.get(i));
                else
                    strValues.append(curValues.get(i)).append(", ");
            }
            payloadDTOS.add(new PayloadDTO(time.toString(), strValues.toString()));
        }

        return payloadDTOS;
    }
}
