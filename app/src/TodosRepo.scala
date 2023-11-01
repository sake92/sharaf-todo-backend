package ba.sake.todo.backend

import java.util.UUID

case class Todo(id: UUID, title: String, completed: Boolean, url: String, order: Option[Int])

// dont do this synchronized stuff at home!
class TodosRepo(baseUrl: String) {

  private var todosRef = List.empty[Todo]

  def getTodos(): List[Todo] = todosRef.synchronized {
    todosRef
  }

  def getTodo(id: UUID): Todo = todosRef.synchronized {
    todosRef.find(_.id == id).get
  }

  def add(req: CreateTodo): Todo = todosRef.synchronized {
    val id = UUID.randomUUID()
    val newTodo = Todo(id, req.title, false, s"${baseUrl}/todos/${id}", req.order)
    todosRef = todosRef.appended(newTodo)
    newTodo
  }

  def set(t: Todo): Unit = todosRef.synchronized {
    todosRef = todosRef.filterNot(_.id == t.id).appended(t)
  }

  def delete(id: UUID): Unit = todosRef.synchronized {
    todosRef = todosRef.filterNot(_.id == id)
  }

  def deleteAll(): Unit = todosRef.synchronized {
    todosRef = List.empty
  }
}
