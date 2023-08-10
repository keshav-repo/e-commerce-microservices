FROM openjdk:17-oracle

COPY target/inventoryservice-0.0.1-SNAPSHOT.jar /inventory-app.jar

EXPOSE 8200

ENTRYPOINT ["java", "-jar", "/inventory-app.jar"]
