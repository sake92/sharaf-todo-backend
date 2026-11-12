FROM eclipse-temurin:21-jdk-noble AS build

WORKDIR /build
RUN apt-get update && apt-get install -y --no-install-recommends clang libssl-dev curl && rm -rf /var/lib/apt/lists/*
RUN curl -fsSL https://repo.maven.apache.org/maven2/org/scala-sbt/sbt-launch/2.0.12/sbt-launch-2.0.12.jar -o /sbt-launch.jar
COPY build.sbt ./
COPY project/ project/
COPY app/ app/
RUN java -jar /sbt-launch.jar nativeLink

FROM ubuntu:24.04
RUN apt-get update && apt-get install -y --no-install-recommends libssl3 && rm -rf /var/lib/apt/lists/*
WORKDIR /app
COPY --from=build /build/target/native/sharaf-todo-backend server
EXPOSE 8181
ENTRYPOINT ["./server"]
