FROM amazoncorretto:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} aop-realization-examples-1.0-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/aop-realization-examples-1.0-SNAPSHOT.jar"]