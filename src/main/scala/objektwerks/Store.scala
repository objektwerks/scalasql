package objektwerks

import com.typesafe.config.Config

import java.nio.file.{Files, Path}

import org.h2.jdbcx.JdbcConnectionPool

import scala.util.Using

import scalasql.core.DbClient
import scalasql.H2Dialect.*
// import scalasql.simple.{*, given}

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
  private val db: DbClient.DataSource = Store.createDataSource(config)

  def close(): Unit =
    db.asInstanceOf[JdbcConnectionPool].dispose()

  def addTodo(todo: Todo): Todo = ???

  def updateTodo(todo: Todo): Int = ???

  def listTodos(): Seq[Todo] = ???