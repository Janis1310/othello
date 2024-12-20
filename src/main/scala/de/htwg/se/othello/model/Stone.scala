package de.htwg.se.othello.model

import de.htwg.se.othello.model.Interface.StoneComponent
// enum Stone {
//   case Black, White, Empty

//   override def toString: String = this match
//     case Black => "S"    
//     case White => "W"
//     case Empty => "."

//   def flip: Stone = this match
//     case Black => White
//     case White => Black
//     case Empty => throw new UnsupportedOperationException("Empty cannot be flipped")
// }

// sealed trait Stone {
//   def flip: Stone
//   override def toString: String
// }

// object Stone {
//   case object Black extends StoneComponent {
//     override def flip: Stone = White
//     override def toString: String = "S"
//   }

//   case object White extends Stone {
//     override def flip: Stone = Black
//     override def toString: String = "W"
//   }

//   case object Empty extends Stone {
//     override def flip: Stone = throw new UnsupportedOperationException("Empty cannot be flipped")
//     override def toString: String = "."
//   }

//   // Exporte für Zugriff als Stone.White, Stone.Black, usw.
//   val BlackStone: Stone = Black
//   val WhiteStone: Stone = White
//   val EmptyStone: Stone = Empty
// }
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
    override def flip: StoneComponent = throw new UnsupportedOperationException("Empty cannot be flipped")
    override def toString: String = "."
  }


  type Stone = StoneComponent

  val BlackStone: Stone = Black
  val WhiteStone: Stone = White
  val EmptyStone: Stone = Empty
}


