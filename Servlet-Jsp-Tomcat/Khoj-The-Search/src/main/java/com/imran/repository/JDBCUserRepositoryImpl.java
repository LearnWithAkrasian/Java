package com.imran.repository;

import com.imran.domain.User;
import com.imran.jdbc.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
// This class is implementation of UserRepository interface.
public class JDBCUserRepositoryImpl implements UserRepository{
    private static final Logger LOGGER
            = LoggerFactory.getLogger(JDBCUserRepositoryImpl.class);
    private DataSource dataSource
            = ConnectionPool.getInstance().getDataSource();

    // The value in these question marks in the query
    // will be added in runtime.
    private static final String INSERT_USER = "insert into customer (" +
            " username, " + // 1
            " password, " + // 2
            " version, " + // 3
            " date_created, " + // 4
            " date_last_updated " + // 5
            " ) " +
            " values(?, ?, ?, ?, ?)";

    private static final String FIND_ALL_USER = "select * from customer";



    // Saving a new user which is signed up..
    @Override
    public void save(User user) {

        try (var connection = dataSource.getConnection();
             var prstmnt = connection.prepareStatement(INSERT_USER)) {
            prstmnt.setString(1, user.getUsername());
            prstmnt.setString(2, user.getPassword());
            prstmnt.setLong(3, user.getVersion() + 1L);
            prstmnt.setTimestamp(4, Timestamp.valueOf(user.getDateCreated()));
            prstmnt.setTimestamp(5, Timestamp.valueOf(user.getDateLastUpdated()));

            prstmnt.execute();

        } catch (SQLException e) {
            throw new RuntimeException("Unable to insert data into database.");
        }
    }

    // This is finding an existing user from database.
    @Override
    public Optional<User> findByUsername(String username) {
        List<User> USERS = new ArrayList<>();

        try (var connection = dataSource.getConnection();
             var prstmnt = connection.prepareStatement(FIND_ALL_USER)){
            var resultSet = prstmnt.executeQuery();

            while (resultSet.next()) {
                User user = extractUser(resultSet);
                USERS.add(user);
            }
            return USERS.stream()
                    .filter(user -> Objects.equals(user.getUsername(), username))
                    .findFirst();
        } catch (Exception e) {
            throw new RuntimeException("Unable to insert data into database.");
        }
    }

    private User extractUser(ResultSet resultSet) throws SQLException{
        var user = new User();
        user.setId(resultSet.getLong("id"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setVersion(resultSet.getLong("version"));
        user.setDateCreated(resultSet
                .getTimestamp("date_created")
                .toLocalDateTime()
        );
        user.setDateLastUpdated(resultSet
                .getTimestamp("date_last_updated")
                .toLocalDateTime()
        );
        return user;
    }
}
