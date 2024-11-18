package de.htwg.se.othello.model

import de.htwg.se.othello.model.{Board, Stone} // import board and stone
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class PlayerSpec extends AnyWordSpec with Matchers {

     "A Player" should {
    "have the correct name and stone" in {
      val player = Player("Bob", Stone.White)
      player.name shouldEqual "Bob"
      player.stone shouldEqual Stone.White
    }

    "return the correct format in toString" in {
      val player = Player("Alice", Stone.Black)
      player.toString shouldEqual "Alice: S"  // Erwartetes Format: Name und Symbol des Steins
    }
  }


  
}

