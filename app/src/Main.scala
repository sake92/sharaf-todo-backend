package ba.sake.todo.backend

import java.util.UUID
import io.undertow.Undertow
import com.typesafe.config.ConfigFactory
import ba.sake.tupson.*
import ba.sake.tupson.config.*
import ba.sake.validson.*
import ba.sake.sharaf.handlers.cors.CorsSettings
import ba.sake.sharaf.*, routing.*

@main def main: Unit = {

  val config = ConfigFactory.load.parseConfig[TodoBackendConfig]
  val todosRepo = new TodosRepo(config.baseUrl)

  val routes = Routes {
    case GET() -> Path() =>
      val res = todosRepo.getTodos().map(TodoResponse.fromTodo)
      Response.withBody(res)

    case GET() -> Path("todos", param[UUID](id)) =>
      val todo = todosRepo.getTodo(id)
      Response.withBody(TodoResponse.fromTodo(todo))

    case POST() -> Path() =>
      val reqBody = Request.current.bodyJsonValidated[CreateTodo]
      val newTodo = todosRepo.add(reqBody)
      Response.withBody(TodoResponse.fromTodo(newTodo))

    case DELETE() -> Path() =>
      todosRepo.deleteAll()
      Response.withBody(List.empty[TodoResponse])

    case DELETE() -> Path("todos", param[UUID](id)) =>
      todosRepo.delete(id)
      Response.withBody(todosRepo.getTodos().map(TodoResponse.fromTodo))

    case PATCH() -> Path("todos", param[UUID](id)) =>
      val reqBody = Request.current.bodyJsonValidated[PatchTodo]
      var todo = todosRepo.getTodo(id)
      reqBody.title.foreach(t => todo = todo.copy(title = t))
      reqBody.completed.foreach(c => todo = todo.copy(completed = c))
      reqBody.url.foreach(u => todo = todo.copy(url = u))
      reqBody.order.foreach(o => todo = todo.copy(order = Some(o)))
      todosRepo.set(todo)
      Response.withBody(TodoResponse.fromTodo(todo))

  }

  val port = 8181

  val server = Undertow
    .builder()
    .addHttpListener(port, "0.0.0.0")
    .setHandler(
      SharafHandler(routes).withCorsSettings(
        CorsSettings.default.withAllowedOrigins(Set("https://todobackend.com"))
      )
    )
    .build()

  server.start()

  println(s"Started HTTP server at http://localhost:${port}")
}

// app config
case class TodoBackendConfig(baseUrl: String) derives JsonRW
