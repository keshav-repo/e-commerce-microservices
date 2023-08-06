FROM openjdk:17-oracle

COPY target/catalog-0.0.1-SNAPSHOT.jar /catalog-app.jar

EXPOSE 8100

ENTRYPOINT ["java", "-jar", "/catalog-app.jar"]

