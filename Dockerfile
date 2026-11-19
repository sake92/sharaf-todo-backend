# syntax=docker/dockerfile:1
FROM ghcr.io/graalvm/native-image-community:25-muslib AS build
ADD --checksum=sha256:2bed730595bfa7de20a4632b85d3ab51fc2c0de5e6d9dd17efeaee58491706f4 \
    https://repo.maven.apache.org/maven2/org/scala-sbt/sbt-launch/2.1.0/sbt-launch-2.1.0.jar /opt/sbt-launch.jar
WORKDIR /build
COPY build.sbt ./
COPY project/ project/
COPY src/ src/
RUN java -jar /opt/sbt-launch.jar 'set nativeImageOptions ++= Seq("--static", "--libc=musl")' nativeImage

FROM alpine:3.24
WORKDIR /app
COPY --from=build /build/target/out/jvm/scala-3.9.0/sharaf-todo-backend/native-image/sharaf-todo-backend server
EXPOSE 8181
ENTRYPOINT ["./server"]
