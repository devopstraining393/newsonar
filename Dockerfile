FROM maven:3.8.1-jdk-11-slim AS extract
WORKDIR /app
COPY /target/spring-boot-base-0.0.1-SNAPSHOT.jar ./app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM gcr.io/distroless/java:11 as runtime

USER nonroot:nonroot
WORKDIR /application

# copy layers from build image to runtime image as nonroot user
COPY --from=extract --chown=nonroot:nonroot /app/dependencies/ ./
COPY --from=extract --chown=nonroot:nonroot /app/snapshot-dependencies/ ./
COPY --from=extract --chown=nonroot:nonroot /app/spring-boot-loader/ ./
COPY --from=extract --chown=nonroot:nonroot /app/application/ ./

EXPOSE 8080
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]