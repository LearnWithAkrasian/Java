# Problems that I faced during the learing of Java and Servlet.

- When I tried to run gradle build, I encountered an error due to the use of the testCompile configuration in the build.gradle file. Since I was using Gradle version 8, the testCompile configuration was no longer supported.
To fix the issue, I replaced testCompile with testImplementation, which is the correct configuration in Gradle 8.
