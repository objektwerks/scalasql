package objektwerks

import com.typesafe.config.Config

import scalasql.H2Dialect.*
import scalasql.simple.*

final class TodoStore(config: Config):
  private val datasource = Store.createDataSource(config)

  def addTodo(todo: Todo): Todo =
    val id = datasource.transaction { tx =>
      tx.run(
        Todo
          .insert
          .columns(_.task := todo.task)
      )
    }
    todo.copy(id = id)

  def updateTodo(todo: Todo): Count =
    datasource.transaction { tx =>
      tx.run(
        Todo
          .update(_.id === todo.id)
          .set(_.task := todo.task)
      )
    }

  def listTodos(): Seq[Todo] =
    datasource.transaction { tx =>
      tx.run(
        Todo.select
      )
    }