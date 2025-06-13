package objektwerks

import scalasql.namedtuples.SimpleTable

final case class Todo(id: Int = 0, task: String)

object Todo extends SimpleTable[Todo]