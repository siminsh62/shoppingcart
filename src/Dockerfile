FROM adoptopenjdk/openjdk11
ARG JAR_FILE=Customer/target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]