FROM openjdk:17-oracle

COPY target/ServiceDiscovery-0.0.1-SNAPSHOT.jar /ServiceDiscovery.jar

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "/ServiceDiscovery.jar"]

