package de.htwg.se.othello.model.Playercomponents

import de.htwg.se.othello.model.BoardComponents.StoneComponent
import play.api.libs.json.*
import de.htwg.se.othello.model.BoardComponents.BoardBaseImpl.Stone

import scala.collection.immutable.Queue

trait Player {
  def name: String
  def stone: StoneComponent
  def role: String

  override def equals(obj: Any): Boolean = obj match {
    case that: Player =>
      this.name == that.name &&
        this.stone == that.stone &&
        this.role == that.role
    case _ => false
  }
}

case class HumanPlayer(val name: String, val stone: StoneComponent)
    extends Player {
  val role: String = "Human"
  override def toString: String = s"$name: $stone"
}

case class AIPlayer(val name: String, val stone: StoneComponent)
    extends Player {
  val role: String = "AI"
  override def toString: String = s"$name (AI): $stone"
}

object Player {
  def apply(name: String, stone: StoneComponent, kind: String) = kind match {
    case "Human" => new HumanPlayer(name, stone)
    case "AI"    => new AIPlayer(name, stone)
  }

  implicit val playerWrites: Writes[Player] = new Writes[Player] {
  override def writes(player: Player): JsValue = player match {
    case human: HumanPlayer =>
      Json.obj(
        "name" -> human.name,
        "stone" -> Json.toJson(human.stone),
        "role" -> human.role
      )
    case ai: AIPlayer =>
      Json.obj(
        "name" -> ai.name,
        "stone" -> Json.toJson(ai.stone),
        "role" -> ai.role
      )
  }
}

  implicit val playerReads: Reads[Player] = Reads { json =>
    (json \ "role").as[String] match {
      case "Human" =>
        for {
          name <- (json \ "name").validate[String]
          stone <- (json \ "stone").validate[StoneComponent]
        } yield HumanPlayer(name, stone)
      case "AI" =>
        for {
          name <- (json \ "name").validate[String]
          stone <- (json \ "stone").validate[StoneComponent]
        } yield AIPlayer(name, stone)
      case e => JsError(s"Unbekannte Spielerrolle: $e")
    }
  }

  private var players: Queue[Player] = Queue()

  // Methode zum Setzen der Spieler
  def setPlayers(players: Queue[Player]): Unit = {
    this.players = players
  }

  def getPlayers: Queue[Player] = players

  def getCurrentPlayer: Player = players.head

}
