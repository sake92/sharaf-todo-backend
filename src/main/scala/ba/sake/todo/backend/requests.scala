package ba.sake.todo.backend

import ba.sake.tupson.JsonRW

case class CreateTodo(
    title: String,
    order: Option[Int]
) derives JsonRW

case class PatchTodo(
    title: Option[String],
    completed: Option[Boolean],
    url: Option[String],
    order: Option[Int]
) derives JsonRW
