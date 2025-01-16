package de.htwg.se.othello.model

import de.htwg.se.othello.model.BoardComponents.BoardBaseImpl.Stone
import de.htwg.se.othello.model.Playercomponents.{AIPlayer, HumanPlayer, Player}
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
  "Player equals method" should {

    "return true for two HumanPlayers with the same attributes" in {
      val player1 = new HumanPlayer("Player1", Stone.Black)
      val player2 = new HumanPlayer("Player1", Stone.Black)

      player1.equals(player2) shouldBe true
    }

    "return false for two HumanPlayers with different names" in {
      val player1 = new HumanPlayer("Player1", Stone.Black)
      val player2 = new HumanPlayer("Player2", Stone.Black)

      player1.equals(player2) shouldBe false
    }
    "return false for two HumanPlayers with different stones" in {
      val player1 = new HumanPlayer("Player1", Stone.Black)
      val player2 = new HumanPlayer("Player1", Stone.White)

      player1.equals(player2) shouldBe false
    }

    "return false for a HumanPlayer and an AIPlayer with the same attributes" in {
      val humanPlayer = new HumanPlayer("Player1", Stone.Black)
      val aiPlayer = new AIPlayer("Player1", Stone.Black)

      humanPlayer.equals(aiPlayer) shouldBe false
    }

    "return true for two AIPlayers with the same attributes" in {
      val player1 = new AIPlayer("Player2", Stone.White)
      val player2 = new AIPlayer("Player2", Stone.White)

      player1.equals(player2) shouldBe true
    }

    "return false for two AIPlayers with different names" in {
      val player1 = new AIPlayer("Player1", Stone.White)
      val player2 = new AIPlayer("Player2", Stone.White)

      player1.equals(player2) shouldBe false
    }

    "return false for two AIPlayers with different stones" in {
      val player1 = new AIPlayer("Player1", Stone.Black)
      val player2 = new AIPlayer("Player1", Stone.White)

      player1.equals(player2) shouldBe false
    }
  }
}
