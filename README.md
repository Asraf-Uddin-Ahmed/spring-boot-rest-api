# spring-boot-rest-api
REST API sample created by Spring Boot
# Configuration
Add below two lines to *STS.ini* or *eclipse.ini* before *-vmargs* if needed
```
-vm
C:\Program Files\Java\jdk1.8.0_171\bin\javaw.exe
```
# Build and Run
To create executable jar file run following command:
```
mvn clean package
```
To create executable jar without running any test:
```
mvn clean package -Dmaven.test.skip=true
```
To run jar file by default properties (or profile) file:
```
java -jar target/spring-boot-hibernate-datajpa.jar
```
To run jar file by specific profile (here is *prod*, which is fetching data from *application-prod.properties*) file:
```
java -jar target/spring-boot-hibernate-datajpa.jar --spring.profiles.active=prod
```
# References
1. [Configure reverse engineering](http://www.codejava.net/frameworks/hibernate/java-hibernate-reverse-engineering-tutorial-with-eclipse-and-mysql)
2. [Customize hibernate code generating configuration](http://www.codejava.net/frameworks/hibernate/how-to-customize-hibernate-reverse-engineering-code-generation)
3. [Querydsl and RSQL](https://github.com/eugenp/tutorials/tree/master/spring-rest-query-language)
4. [RSQL parser](https://github.com/jirutka/rsql-parser)
5. [Querydsl join](https://stackoverflow.com/questions/47661028/querydsl-how-to-write-a-complex-query?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa) 
6. [To generate Querydsl code](https://stackoverflow.com/questions/24482259/eclipse-issue-with-maven-build-and-jdk-when-generating-qclasses-in-querydsl) 
7. [Profile based development](https://www.youtube.com/watch?v=0zjQX7WwjrI&feature=youtu.be&list=PLGDwUiT1wr6-Fn3N2oqJpTdhGjFHnIIKY)
8. [Spring security role based response shaping](https://stackoverflow.com/questions/17276081/spring-3-2-filtering-jackson-json-output-based-on-spring-security-role)
9. [SpEL based validation](http://javatar81.blogspot.com/2016/06/hibernate-validator-spring-expression.html)
10. [Spring Boot Server Sent Events](https://www.baeldung.com/spring-server-sent-events)
11. [Server Sent Events with Scheduler](https://www.linkedin.com/pulse/server-sent-events-sse-spring-boot-aliaksandr-liakh)
