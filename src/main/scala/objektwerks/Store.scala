package objektwerks

import com.typesafe.config.Config

import java.nio.file.{Files, Path}

import org.h2.jdbcx.JdbcConnectionPool

import scala.util.Using

import scalasql.core.DbClient
import scalasql.dialects.H2Dialect.*
import scalasql.simple.*

private object Store:
  private def createDataSource(config: Config): DbClient.DataSource =
    val datasource = JdbcConnectionPool.create(
      config.getString("ds.url"),
      config.getString("ds.user"),
      config.getString("ds.password")
    )

    Using.Manager( use =>
      val connection = use( datasource.getConnection )
      val statement = use( connection.createStatement )
      val sql = Files.readString( Path.of( config.getString("ds.ddl") ) )
      statement.execute(sql)
    )

    DbClient.DataSource(
      datasource,
      config = new scalasql.Config {}
    )

final class Store(config: Config):
  private val ds = Store.createDataSource(config)

  def close(): Unit =
    ds.asInstanceOf[JdbcConnectionPool].dispose()

  def addTodo(todo: Todo): Todo =
    val id = ds.transaction { tx =>
      tx.run(
        Todo.insert.columns(_.task := todo.task)
      )
    }
    todo.copy(id = id)

  def updateTodo(todo: Todo): Int =
    ds.transaction { tx =>
      tx.run(
        Todo.update(_.id === todo.id).set(_.task := todo.task)
      )
    }

  def listTodos(): Seq[Todo] =
    ds.transaction { tx =>
      tx.run(
        Todo.select
      )
    }