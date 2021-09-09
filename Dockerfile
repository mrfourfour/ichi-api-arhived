FROM openjdk:12
ARG JAR_FILE=ichi-server/build/libs/ichi-server-1.0.jar
ADD ${JAR_FILE} ichi-server.jar

ENTRYPOINT ["java", "-jar", "/ichi-server.jar"]