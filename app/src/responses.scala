package ba.sake.todo.backend

import ba.sake.tupson.JsonRW

case class TodoResponse(
    title: String,
    completed: Boolean,
    url: String,
    order: Option[Int]
) derives JsonRW

object TodoResponse:
  def fromTodo(t: Todo): TodoResponse =
    TodoResponse(t.title, t.completed, t.url, t.order)
