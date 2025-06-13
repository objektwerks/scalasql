package objektwerks

import com.typesafe.config.Config

import java.nio.file.{Files, Path}

import org.h2.jdbcx.JdbcConnectionPool

import scala.util.Using

import scalasql.H2Dialect.*
import scalasql.core.DbClient
import scalasql.simple.*

private object Store:
  private def init(config: Config): DbClient.DataSource =
    val datasource = JdbcConnectionPool.create(
      config.getString("ds.url"),
      config.getString("ds.user"),
      config.getString("ds.password")
    )

    Using.Manager( manager =>
      val connection = manager( datasource.getConnection )
      val statement = manager( connection.createStatement )
      val sql = Files.readString( Path.of( config.getString("ds.ddl") ) )
      statement.execute(sql)
    )

    DbClient.DataSource(datasource)

final class Store(config: Config):
  private val datasource = Store.init(config)

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