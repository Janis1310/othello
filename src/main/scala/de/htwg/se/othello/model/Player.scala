package de.htwg.se.othello.model

// Product (interface)
trait Player {
  def name: String
  def stone: Stone
  def role: String  // Add this method to define the player's role
  override def toString: String = s"$name: $stone"
}

// concreteProduct: HumanPlayer
private class HumanPlayer(val name: String, val stone: Stone) extends Player {
  val role: String = "Human"  // Define the role for HumanPlayer
  override def toString: String = s"$name: $stone"
}

// concreteProduct: AIPlayer
private class AIPlayer(val name: String, val stone: Stone) extends Player {
  val role: String = "AI"  // Define the role for AIPlayer
  override def toString: String = s"$name (AI): $stone"
}

object Player {
  def apply(name: String, stone: Stone, kind: String) = kind match {
    case "Human" => new HumanPlayer(name, stone)
    case "AI" => new AIPlayer(name, stone)
  }
}
