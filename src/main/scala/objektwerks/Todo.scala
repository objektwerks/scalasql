package objektwerks

import scalasql.namedtuples.SimpleTable

type Count = Int

final case class Todo(id: Int = 0, task: String) derives CanEqual

object Todo extends SimpleTable[Todo]