# sharaf-todo-backend

[Sharaf](https://github.com/sake92/sharaf)'s implementation of [Todo-Backend](https://todobackend.com)

Served at https://p01--backend--zv8j4jzgjfty.code.run/  
Thanks to northflank.com ! <3


---

The server uses Sharaf's Potateh adapter 0.24.0 and GraalVM Native Image. Build and run from the repo root (requires GraalVM 25 with `native-image` and a C toolchain):

```sh
sbt nativeImage
./target/out/jvm/scala-3.9.0/sharaf-todo-backend/native-image/sharaf-todo-backend
```

Alternatively, build the statically linked executable in an Alpine container with `docker build -t sharaf-todo-backend .`, then run it with `docker run --rm -p 8181:8181 sharaf-todo-backend`.

Set `BASE_URL` to the public server URL when it differs from `http://localhost:8181`.

## testing at todobackend.com

- run as above
- open https://todobackend.com/specs/index.html in browser
- enter http://localhost:8181 as target (or https://p01--backend--zv8j4jzgjfty.code.run)

