# build
FROM eclipse-temurin:17 as build

COPY mill .
COPY .mill-version .
COPY build.sc .
COPY app/ app/

RUN ./mill app.assembly

# run
FROM eclipse-temurin:17.0.8_7-jre-alpine

COPY --from=build out/app/assembly.dest/out.jar app.jar

EXPOSE 8181

ENTRYPOINT ["java", "-jar", "app.jar"]
