FROM openjdk:11
VOLUME /tmp
ADD target/favouritefilms-0.0.1-SNAPSHOT.jar app.jar
ENV SPRING_DATASOURCE_URL jdbc:postgresql://db:5432/postgres
ENTRYPOINT ["java", "-jar", "/app.jar"]
EXPOSE 8080