package objektwerks

import com.typesafe.config.Config

import java.nio.file.{Files, Path}

import org.h2.jdbcx.JdbcConnectionPool

import scala.util.Using

import scalasql.H2Dialect.*
import scalasql.core.DbClient

object Store:
  def createDataSource(config: Config): DbClient.DataSource =
    val datasource = JdbcConnectionPool.create(
      config.getString("ds.url"),
      config.getString("ds.user"),
      config.getString("ds.password")
    )

    Using.Manager( manager =>
      val connection = manager( datasource.getConnection() )
      val statement = manager( connection.createStatement() )
      val sql = Files.readString( Path.of( config.getString("ds.ddl") ) )
      statement.execute(sql)
    )

    DbClient.DataSource(datasource)