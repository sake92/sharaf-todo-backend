# sharaf-todo-backend

[Sharaf](https://github.com/sake92/sharaf)'s implementation of [Todo-Backend](https://todobackend.com)

Served at https://p01--backend--zv8j4jzgjfty.code.run/  
Thanks to northflank.com ! <3


---

Build and run from the repo root (requires JDK 21, Clang and OpenSSL development libraries):

```sh
sbt nativeLink
./target/native/sharaf-todo-backend
```


Set `BASE_URL` to the public server URL when it differs from `http://localhost:8181`.

## testing at todobackend.com

- run as above
- open https://todobackend.com/specs/index.html in browser
- enter http://localhost:8181 as target (or https://p01--backend--zv8j4jzgjfty.code.run)

