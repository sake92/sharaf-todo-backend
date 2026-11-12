FROM eclipse-temurin:21-jdk AS build

WORKDIR /build
RUN apt-get update && apt-get install -y --no-install-recommends curl && rm -rf /var/lib/apt/lists/*
RUN curl -fsSL https://repo.maven.apache.org/maven2/org/scala-sbt/sbt-launch/2.0.12/sbt-launch-2.0.12.jar -o /sbt-launch.jar
COPY build.sbt ./
COPY project/ project/
COPY app/ app/
RUN java -jar /sbt-launch.jar assembly

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /build/target/app.jar app.jar
EXPOSE 8181
ENTRYPOINT ["java", "-Dconfig.override_with_env_vars=true", "-jar", "app.jar"]
