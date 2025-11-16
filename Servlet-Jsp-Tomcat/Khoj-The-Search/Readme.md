# Khoj The Search

## About
This project is simply a demo demonstrating basic features which is only the tip of an iceberg.
This project might (and certainly does) have bugs, vulnerabilities or some other types of issues.
So, contributions are always welcome :)

I attempted to implement this small project using the principles of `Object-Oriented Programming (OOP)`.
Specifically, I applied the concept of `Loosely Coupled` design by utilizing interfaces. 
I also applied the concept of `Singleton Pattern` in [ValidationUtil](https://github.com/ImranHossainFakir/Simple-Projects-with-JAVA/blob/main/Khoj-The-Search/src/main/java/com/imran/util/ValidationUtil.java) class. Each interface is designed to handle a single, specific task.
However, the implementation may not fully adhere to `OOP` principles. 

In this project there have multiple packages in the [src/main](https://github.com/ImranHossainFakir/Simple-Projects-with-JAVA/tree/main/Khoj-The-Search/src/main) directory.

1. [java/com/imran](#1-javacomimran)
2. [resources](#2-resources)
3. [webapp](#3-webapp)

### 1. java/com/imran
- `annotations`: Holding customized annotation which is used in `DTO` classes.
- `domain`: Classes that store data in the database.
- `dto(Data Transfer Object)`: Classes or objects handling data communication with the client, sending and receiving information.  
- `exceptions`: Classes for customized exceptions.
- `filter`: Objects Filtering every request from the client.
- `jdbc`: ConnectionPool object which is responsible to create connection with database, store connections to the pool.
- `repository`: Objects which are responsible for the `CRUD` operations.
- `services`: Business Logics.
- `util`: Validator Objects.
- `web`: Servlet objects which are responsible for receiving requests and sending responses.

### 2. resources
- `db.properties`: In this file, we configure the properties for our database connection.

### 3. webapp
- `images`: Images.
- `WEB-INF`: Web pages.

## Features Include
1. **Login And Registration.**
   ```md
   Users can create an account by providing a unique username and password. Subsequently,
   they can log in using the established credentials.
   ```
   ![Alter text](https://github.com/ImranHossain00/Simple-Projects-with-JAVA/blob/main/Khoj-The-Search/screenshots/khoj_signup.jpg?raw=true)
   ![Alter text](https://github.com/ImranHossain00/Simple-Projects-with-JAVA/blob/main/Khoj-The-Search/screenshots/khoj_login.jpg?raw=true)
2. **Searching for one or more integer (non-negative) numbers within a list of integers (non-negative).**
   ```md
   After logging in, users are redirected to the home page where they can input 
   one or more integer values and specify one or more integer values as search criteria.
   A button labeled `Khoj` is provided. Upon entering valid input, users can initiate 
   a search to determine whether the search values exist within the provided input values.
   The result, either `true` or `false`, will be displayed.
   ```
   ![Alter text](https://github.com/ImranHossain00/Simple-Projects-with-JAVA/blob/main/Khoj-The-Search/screenshots/khoj_input_result.jpg?raw=true)
3. **Rest Api.**
   ```md
   The `REST API` will display input values associated with
   a specific ID number (not necessarily belonging to the logged-in person) within 
   a specified time range.
   
   The response will provide a `JSON` object.
   ```
   ![Alter text](https://github.com/ImranHossain00/Simple-Projects-with-JAVA/blob/main/Khoj-The-Search/screenshots/khoj_rest_api.jpg?raw=true)
   ![Alter text](https://github.com/ImranHossain00/Simple-Projects-with-JAVA/blob/main/Khoj-The-Search/screenshots/khoj_rest_api_1.jpg?raw=true)


## Prerequisites
My device is equipped with:
```
Linux Operating System (Ubuntu 23.04 [lunar])
JDK - 11
Tomcat server 9.
Locally installed MySQL.
Compatible IDE, Intellij IDEA recommended for this project.
Gradle 7.2 (Build Tool)
```

## Database and Tables
**The database and table creation queries are provided in [Queries.ddl](https://github.com/ImranHossainFakir/Simple-Projects-with-JAVA/blob/main/Khoj-The-Search/Queries.ddl).**
- **Database name:** ``khojTheSearch``
- **Tables name:** 
  1. `customer`
     ![Alter text](https://github.com/ImranHossain00/Simple-Projects-with-JAVA/blob/main/Khoj-The-Search/screenshots/khoj_database1.jpg?raw=true)
  2. `user_values`
     ![Alter text](https://github.com/ImranHossain00/Simple-Projects-with-JAVA/blob/main/Khoj-The-Search/screenshots/khoj_database2.jpg?raw=true)


## Arrived Quesions
- **Connection with database failed:** When we try to connect our database with the 
   DataSource object it was not working properly. It was showing this error:

  ![Alter text](https://github.com/ImranHossain00/Simple-Projects-with-JAVA/blob/main/Khoj-The-Search/screenshots/DatabaseError_1.jpg?raw=true)

  When we used `Connection Pool` from the Library `HikariCP` it is now working as much as good.
