## Build the application
FROM maven:3.9.5-amazoncorretto-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install -DskipTests

## Run the application
FROM openjdk:21-oracle
WORKDIR /app
COPY --from=build /app/target/grocery-rest-api-0.0.1-SNAPSHOT.jar ./grocery-rest-api.jar
EXPOSE 9090

ENV MYSQL_ADDON_URI_GDB mysql://usvi4cuxiug0owth:3JwmMnKOZrQemuOaQBaH@bagrzfxdo9lhzk8vaqpf-mysql.services.clever-cloud.com:3306/bagrzfxdo9lhzk8vaqpf
ENV MYSQL_ADDON_USER_GDB usvi4cuxiug0owth
ENV MYSQL_ADDON_PASSWORD_GDB 3JwmMnKOZrQemuOaQBaH
ENV OUTLOOK_EMAIL is2solutions@outlook.com
ENV OUTLOOK_PASSWORD Bojack2020!
ENV TWILIO_AUTH_TOKEN 7e2a958b812547e9a664304f5aaabdbb
ENV TWILIO_SID AC6b743480d616119c6d1b9f4847a4d863

CMD [ "java", "-jar", "grocery-rest-api.jar" ]