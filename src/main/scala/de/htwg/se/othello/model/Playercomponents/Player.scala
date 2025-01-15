package de.htwg.se.othello.model.Playercomponents

import de.htwg.se.othello.model.BoardComponents.StoneComponent
import play.api.libs.json._
import de.htwg.se.othello.model.BoardComponents.BoardBaseImpl.Stone

// Product (interface)
trait Player {
  def name: String
  def stone: StoneComponent
  def role: String // Add this method to define the player's role

  override def equals(obj: Any): Boolean = obj match {
    case that: Player =>
      this.name == that.name &&
        this.stone == that.stone &&
        this.role == that.role
    case _ => false
  }
}

// concreteProduct: HumanPlayer
case class HumanPlayer(val name: String, val stone: StoneComponent)
    extends Player {
  val role: String = "Human" // Define the role for HumanPlayer
  override def toString: String = s"$name: $stone"
}

// concreteProduct: AIPlayer
case class AIPlayer(val name: String, val stone: StoneComponent)
    extends Player {
  val role: String = "AI" // Define the role for AIPlayer
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
}
