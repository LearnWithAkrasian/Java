package com.imran.repository;

import com.imran.domain.NumberList;
import com.imran.domain.RESTApi;
import com.imran.domain.TimeAndDate;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.Vector;

// This Interface is holding methods which is responsible to do the CRUD operations.
public interface NumberListRepository {
    void save(NumberList list);

    Vector<TimeAndDate> findByTimeAndUserId(RESTApi restApi);
}
