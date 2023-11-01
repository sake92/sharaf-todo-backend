package demo

import java.util.UUID
import io.undertow.Undertow
import ba.sake.tupson.*
import ba.sake.validson.*
import ba.sake.sharaf.*, handlers.*, routing.*

@main def main: Unit = {

  val todosRepo = new TodosRepo

  def todo2Resp(t: Todo): TodoResponse =
    TodoResponse(t.title, t.completed, t.url, t.order)

  val routes: Routes = {
    case GET() -> Path() =>
      Response.withBody(todosRepo.getTodos().map(todo2Resp))

    case GET() -> Path("todos", param[UUID](id)) =>
      val todo = todosRepo.getTodo(id)
      Response.withBody(todo2Resp(todo))

    case POST() -> Path() =>
      val reqBody = Request.current.bodyJsonValidated[CreateTodo]
      val newTodo = todosRepo.add(reqBody)
      Response.withBody(todo2Resp(newTodo))

    case DELETE() -> Path() =>
      todosRepo.deleteAll()
      Response.withBody(List.empty[TodoResponse])

    case DELETE() -> Path("todos", param[UUID](id)) =>
      todosRepo.delete(id)
      Response.withBody(todosRepo.getTodos().map(todo2Resp))

    case PATCH() -> Path("todos", param[UUID](id)) =>
      val reqBody = Request.current.bodyJsonValidated[PatchTodo]
      var todo = todosRepo.getTodo(id)
      reqBody.title.foreach(t => todo = todo.copy(title = t))
      reqBody.completed.foreach(c => todo = todo.copy(completed = c))
      reqBody.url.foreach(u => todo = todo.copy(url = u))
      reqBody.order.foreach(o => todo = todo.copy(order = Some(o)))
      todosRepo.set(todo)
      Response.withBody(todo2Resp(todo))

  }

  val port = 8181

  val server = Undertow
    .builder()
    .addHttpListener(port, "localhost")
    .setHandler(
      SharafHandler(routes)
        .withCorsSettings(CorsSettings(allowedOrigins = Set("https://todobackend.com")))
    )
    .build()

  server.start()

  println(s"Started HTTP server at http://localhost:${port}")
}

case class CreateTodo(title: String, order: Option[Int]) derives JsonRW

case class PatchTodo(title: Option[String], completed: Option[Boolean], url: Option[String], order: Option[Int])
    derives JsonRW

case class TodoResponse(title: String, completed: Boolean, url: String, order: Option[Int]) derives JsonRW
