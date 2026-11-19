# syntax=docker/dockerfile:1
FROM ghcr.io/graalvm/native-image-community:25-muslib AS build
ADD --checksum=sha256:d173ae4bd6eb07fd70b000a12cb7275c7aab26c657e3c9932f497d9578d71eb1 \
    https://repo.maven.apache.org/maven2/org/scala-sbt/sbt-launch/2.0.12/sbt-launch-2.0.12.jar /opt/sbt-launch.jar
WORKDIR /build
COPY build.sbt ./
COPY project/ project/
COPY app/ app/
RUN java -jar /opt/sbt-launch.jar 'set nativeImageOptions ++= Seq("--static", "--libc=musl")' nativeImage

FROM alpine:3.24
WORKDIR /app
COPY --from=build /build/target/out/jvm/scala-3.9.0/sharaf-todo-backend/native-image/sharaf-todo-backend server
EXPOSE 8181
ENTRYPOINT ["./server"]
