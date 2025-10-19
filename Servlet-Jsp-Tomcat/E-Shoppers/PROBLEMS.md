# Problem 1: When I configured the `logback.xml` file to write logs to a file using `RollingFileAppender`, it was not working and was showing this type of error.
```vbnet
|-ERROR in ch.qos.logback.core.pattern.parser.Compiler@... - Failed to instantiate converter class [ch.qos.logback.classic.pattern.LoggerConverter] as a composite converter for keyword [logger] ch.qos.logback.core.util.IncompatibleClassException
|-ERROR in ch.qos.logback.core.pattern.parser.Compiler@... - Failed to create converter for [%logger] keyword
```

Solution: The issue was a syntax error I just wrote ```%logger(36)``` instead of ```%logger{36}``` XD. `%logger{36} tells Logback to output the logger name with a maximum of 36 characters.

# Problem 2: When I ran the root path like “localhost:8080/”, it returned a 404 error.
Solution: The issue was due to not having an index.jsp file. Tomcat, by default, looks for welcome files such as index.jsp or index.html. Once we add the index.jsp file and configure it to redirect to the /home servlet, the issue is resolved.

