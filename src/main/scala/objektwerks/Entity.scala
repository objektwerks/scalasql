package objektwerks

import java.time.LocalDate
import java.util.UUID

import scalasql.namedtuples.SimpleTable

sealed trait Entity:
  val id: Long

object Entity:
  def now: String = LocalDate.now.toString

  def nowMinusOneDay: String = LocalDate.now.minusDays(1).toString

  def nowPlusOneDay: String = LocalDate.now.plusDays(1).toString

final case class Account(
  id: Long = 0,
  license: String = UUID.randomUUID.toString,
  email: String = "",
  pin: String = Pin.newInstance,
  activated: String = Entity.now
) extends Entity derives CanEqual

object Account extends SimpleTable[Account]

final case class Participant(
  id: Long = 0,
  email: String,
  activated: String = Entity.now
) extends Entity derives CanEqual

object Participant extends SimpleTable[Participant]

final case class Survey(
  id: Long = 0,
  accountId: Long,
  title: String,
  created: String = Entity.now,
  released: String = Entity.nowMinusOneDay
) extends Entity derives CanEqual {
    def isReleased: Boolean =
      val created = LocalDate.parse(this.created)
      val released = LocalDate.parse(this.released)
      if released.isEqual(created) || released.isAfter(created) then true
      else false
}

object Survey extends SimpleTable[Survey]

final case class Question(
  id: Long = 0,
  surveyId: Long,
  question: String,
  choices: String, // choices: List[String] can't be used with SimpleTable; so choices would be comma delimited
  typeof: String = "",
  created: String = Entity.now
) extends Entity derives CanEqual

object Question extends SimpleTable[Question]

final case class Answer(
  id: Long = 0,
  surveyId: Long,
  questionId: Long,
  participantId: Long,
  answers: String, // answers: List[String] can't be used with SimpleTable; so answers would be comma delimited
  typeof: String = "",
  answered: String = Entity.now
) extends Entity derives CanEqual

object Answer extends SimpleTable[Answer]