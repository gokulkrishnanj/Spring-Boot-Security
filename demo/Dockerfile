FROM openjdk:17-oracle
WORKDIR /spring
COPY target/demo-0.0.1-SNAPSHOT.jar spring/demo-0.0.1-SNAPSHOT.jar
EXPOSE 8090
ENTRYPOINT ["java","-jar","spring/demo-0.0.1-SNAPSHOT.jar"]