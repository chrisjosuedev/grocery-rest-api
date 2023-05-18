FROM openjdk:19-oracle
EXPOSE 9090
WORKDIR /app
COPY ../target/grocery-rest-api-0.0.1-SNAPSHOT.jar .
ENTRYPOINT [ "java", "-jar", "grocery-rest-api-0.0.1-SNAPSHOT.jar" ]