# Problem 1: When I configured the `logback.xml` file to write logs to a file using `RollingFileAppender`, it was not working and was showing this type of error.
```vbnet
|-ERROR in ch.qos.logback.core.pattern.parser.Compiler@... - Failed to instantiate converter class [ch.qos.logback.classic.pattern.LoggerConverter] as a composite converter for keyword [logger] ch.qos.logback.core.util.IncompatibleClassException
|-ERROR in ch.qos.logback.core.pattern.parser.Compiler@... - Failed to create converter for [%logger] keyword
```

Solution: The issue was a syntax error I just wrote ```%logger(36)``` instead of ```%logger{36}``` XD. `%logger{36} tells Logback to output the logger name with a maximum of 36 characters.

# Problem 2: I tried to use ***Bean Validation*** (Jakarta Validation / Hibernate Validator) in a Java 17 project, but it did not work.

***Solution:***
- Dependencies were outdated (using old javax.validation instead of the new jakarta.validation)
- Gradle versions above 6 require modern dependency coordinates
- Java 17 also requires newer versions of Hibernate Validator
  So validation annotations like:
```java
@NotNull
@Email
@Size(min = 3)
```
did not work or caused class-not-found errors.

# Because of Namespace Change
- Bean Validation moved from javax → jakarta after Java EE became Jakarta EE.
- Old dependency (no longer works with Java 17):

But bean validation API alone is NOT enough.
It also need an implementation, and the modern implementation is:
```java
✔ org.hibernate.validator.hibernate-validator (Jakarta version)
```
If the API is included only , nothing actually performs validation → validation fails silently.

  ![Alter text](https://github.com/LearnWithAkrasian/Java/blob/main/Servlet-Jsp-Tomcat/E-Shoppers/E_Shoppers/src/main/webapp/image/Screenshot%202025-11-17%20at%2003-22-32%20All%20Products.png?raw=true)

