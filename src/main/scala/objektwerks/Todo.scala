package objektwerks

import scalasql.namedtuples.SimpleTable

type Count = Int

object Todo extends SimpleTable[Todo]

final case class Todo(id: Int = 0, task: String)