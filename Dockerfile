FROM eclipse-temurin:21-jdk-alpine-3.24 AS build

WORKDIR /build
RUN apk add --no-cache clang lld build-base openssl-dev curl
RUN curl -fsSL https://repo.maven.apache.org/maven2/org/scala-sbt/sbt-launch/2.0.12/sbt-launch-2.0.12.jar -o /sbt-launch.jar
COPY build.sbt ./
COPY project/ project/
COPY app/ app/
RUN java -jar /sbt-launch.jar nativeLink

FROM alpine:3.24
RUN apk add --no-cache libcrypto3
WORKDIR /app
COPY --from=build /build/target/native/sharaf-todo-backend server
EXPOSE 8181
ENTRYPOINT ["./server"]
