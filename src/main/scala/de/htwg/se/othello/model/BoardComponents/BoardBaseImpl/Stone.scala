package de.htwg.se.othello.model.BoardComponents.BoardBaseImpl

import de.htwg.se.othello.model.BoardComponents.StoneComponent
import play.api.libs.json._

object Stone {
  case object Black extends StoneComponent {
    override def flip: StoneComponent = White
    override def toString: String = "S"
  }

  case object White extends StoneComponent {
    override def flip: StoneComponent = Black
    override def toString: String = "W"
  }

  case object Empty extends StoneComponent {
    override def flip: StoneComponent = throw new UnsupportedOperationException(
      "Empty cannot be flipped"
    )
    override def toString: String = "."
  }

  type Stone = StoneComponent

  val BlackStone: Stone = Black
  val WhiteStone: Stone = White
  val EmptyStone: Stone = Empty
}

