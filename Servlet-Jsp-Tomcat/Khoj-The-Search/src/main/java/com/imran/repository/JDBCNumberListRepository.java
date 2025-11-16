package com.imran.repository;

import com.imran.domain.NumberList;
import com.imran.domain.RESTApi;
import com.imran.domain.TimeAndDate;
import com.imran.jdbc.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;


// This class is implementation of NumberListRepository interface.
public class JDBCNumberListRepository implements NumberListRepository{
    private static final Logger LOGGER
            = LoggerFactory.getLogger(JDBCUserRepositoryImpl.class);
    private DataSource dataSource
            = ConnectionPool.getInstance().getDataSource();

    private static final String SELECT_VALUES_WITHIN_GIVEN_TIME
            = "select user_id, insert_time, user_values " +
             " from user_values " +
             " where user_id=? and insert_time between ? and ?";

    // Saving user input values in ascending order.
    @Override
    public void save(NumberList list) {
        final String INSERT_NUMBER_LIST = makeInsertionQuery(list.getNumberList().size());

        // using try catch with resources for auto close.
        // But in this case close() method will store
        // the connections back to the pool instead close.
        try (var connection = dataSource.getConnection();
             var prstmnt = connection.prepareStatement(INSERT_NUMBER_LIST)){

            int cnt = 1;
            for (Integer val : list.getNumberList()) {
                prstmnt.setLong(cnt++, list.getUser().getId());
                prstmnt.setTimestamp(cnt++, Timestamp.valueOf(list.getInsertTime()));
                prstmnt.setLong(cnt++, val);
                prstmnt.setLong(cnt++, list.getVersion());
                prstmnt.setTimestamp(cnt++, Timestamp.valueOf(list.getDateCreated()));
                prstmnt.setTimestamp(cnt++, Timestamp.valueOf(list.getDateLastUpdated()));
            }

            prstmnt.execute();

        } catch (SQLException e) {
            throw new RuntimeException("Unable to insert the values");
        }
    }

    // Finding input values which are given by the user in a specific time range.
    @Override
    public Vector<TimeAndDate> findByTimeAndUserId(RESTApi restApi) {
        // using try catch with resources for auto close.
        // But in this case close() method will store
        // the connections back to the pool instead close.
        try (var connection = dataSource.getConnection();
             var prstmt = connection.prepareStatement(SELECT_VALUES_WITHIN_GIVEN_TIME)) {
            prstmt.setLong(1, restApi.getUserId());
            prstmt.setTimestamp(2 , Timestamp.valueOf(restApi.getStartTime()));
            prstmt.setTimestamp(3, Timestamp.valueOf(restApi.getEndingTime()));

            var resultSet = prstmt.executeQuery();

            // Finding the values and inserting them into a hashmap
            Vector<TimeAndDate> timeAndDates = extractResultSet(resultSet);

            // For logging view
            if (!timeAndDates.isEmpty()) {
                LOGGER.info("Query fetches some data form database. size of list is {}", timeAndDates.size());
            } else {
                LOGGER.info("There have no data for given time");
            }
            // For logging view

            return timeAndDates;

        } catch (SQLException e) {
            throw new RuntimeException("Unable to search given time");
        }
    }

    // Coping the data (time and input values) which are came form the database.
    private Vector <TimeAndDate> extractResultSet(ResultSet resultSet)
            throws SQLException {

        Vector <TimeAndDate> timesAndDates = new Vector<>();

        // Extracting the values from resultSet;
        while (resultSet.next()) {
            LocalDateTime time
                    = resultSet
                    .getTimestamp("insert_time")
                    .toLocalDateTime();
            Integer val = (int)resultSet.getLong("user_values");

            timesAndDates.add(new TimeAndDate(time, val));
        }
        // Extraction is completed.

        LOGGER.info("Size of tem {}", timesAndDates.size());

        return timesAndDates;
    }

    // For crafting a somewhat complex query for inserting the input values.
    private static String makeInsertionQuery(int len) {

        StringBuilder query = new StringBuilder(
                "INSERT INTO user_values (user_id, insert_time, user_values, version, date_created, date_last_updated)  VALUES ");


        for (int i = 0; i < len; i++) {
            if (i == len-1)
                query.append("(?, ?, ?, ?, ?, ?)");
            else
                query.append("(?, ?, ?, ?, ?, ?),");
        }

        return query.toString();
    }
}