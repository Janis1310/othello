package de.htwg.se.othello.model

import de.htwg.se.othello.model.BoardComponents.BoardBaseImpl.Stone
import de.htwg.se.othello.model.Playercomponents.{AIPlayer, HumanPlayer, Player}
// import board and stone
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class PlayerSpec extends AnyWordSpec with Matchers {

  "Player factory" should {

    "create a HumanPlayer when kind is 'Human'" in {
      val player = Player("A", Stone.Black, "Human")

      player shouldBe a[HumanPlayer]
      player.name shouldBe "A"
      player.stone shouldBe Stone.Black
      player.role shouldBe "Human"
      player.toString shouldBe "A: S"
    }

    "create an AIPlayer when kind is 'AI'" in {
      val player = Player("B", Stone.White, "AI")

      player shouldBe a[AIPlayer]
      player.name shouldBe "B"
      player.stone shouldBe Stone.White
      player.role shouldBe "AI"
      player.toString shouldBe "B (AI): W"
    }

    "throw an exception for an unknown kind" in {
      intercept[MatchError] {
        Player("Unknown", Stone.Black, "UnknownKind")
      }
    }
  }

  "HumanPlayer" should {
    "have a name, stone, role, and a proper toString representation" in {
      val humanPlayer = new HumanPlayer("A", Stone.Black)

      humanPlayer.name shouldBe "A"
      humanPlayer.stone shouldBe Stone.Black
      humanPlayer.role shouldBe "Human"
      humanPlayer.toString shouldBe "A: S"
    }
  }

  "AIPlayer" should {
    "have a name, stone, role, and a proper toString representation" in {
      val aiPlayer = new AIPlayer("B", Stone.White)

      aiPlayer.name shouldBe "B"
      aiPlayer.stone shouldBe Stone.White
      aiPlayer.role shouldBe "AI"
      aiPlayer.toString shouldBe "B (AI): W"
    }
  }
}
