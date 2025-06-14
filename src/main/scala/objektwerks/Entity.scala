package objektwerks

import java.time.LocalDate
import java.util.UUID

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

final case class Participant(
  id: Long = 0,
  email: String,
  activated: String = Entity.now
) extends Entity derives CanEqual