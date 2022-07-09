FROM adoptopenjdk:11-jdk-hotspot

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=ebprod","/app.jar"]