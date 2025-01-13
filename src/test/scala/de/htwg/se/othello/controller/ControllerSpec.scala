package de.htwg.se.othello.controller

import de.htwg.se.othello.Othello
import de.htwg.se.othello.Othello.injector
import de.htwg.se.othello.model.BoardComponents.BoardBaseImpl.{Board, Stone}

import scala.collection.immutable.Queue
import de.htwg.se.othello.controller.ControllerComponents.{ControllerComponent, GameState}
import de.htwg.se.othello.model.BoardComponents.BoardComponent
import de.htwg.se.othello.model.Playercomponents.Player
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class ControllerSpec extends AnyWordSpec with Matchers {

  "A Controller" should {
    "change and get gameState" in {
      val controller = injector.getInstance(classOf[ControllerComponent])
      controller.changeState(GameState.SETUP)
      controller.getGameState shouldBe GameState.SETUP
    }

    "add player1 game State: Ai" in {
      val controller = injector.getInstance(classOf[ControllerComponent])
      controller.setGameMode("Ai")
      controller.changeState(GameState.InputPlayer1)
      controller.addPlayers("Imran")
      controller.getPlayers shouldBe Queue(Player("Imran", Stone.White, "Human"), Player("AI", Stone.Black, "AI"))
    }
    "add player 1 and 2 game State: Human" in {
      val controller = injector.getInstance(classOf[ControllerComponent])
      controller.setGameMode("Human")
      controller.changeState(GameState.InputPlayer1)
      controller.addPlayers("Janis")
      controller.addPlayers("Imran")
      controller.getPlayers shouldBe Queue(Player("Janis", Stone.White, "Human"), Player("Imran", Stone.Black, "Human"))
    }

    "add player not successful" in {
      val controller = injector.getInstance(classOf[ControllerComponent])
      controller.changeState(GameState.BLACK_TURN)
      intercept[IllegalStateException] {
        controller.addPlayers("Imran")
      }.getMessage should include("Spieler können nur im InputPlayer Zustand hinzugefügt werden")

    }
    "Create and get new Board" in {
      val controller = injector.getInstance(classOf[ControllerComponent])
      controller.changeState(GameState.InputBoardSize)
      controller.createNewBoard(8,8)
      controller.getBoard shouldBe new Board(8,8)
    }
    "Create board in wrong state" in {
      val controller = injector.getInstance(classOf[ControllerComponent])
      controller.changeState(GameState.BLACK_TURN)
      intercept[IllegalStateException] {
        controller.createNewBoard(8, 8)
      }.getMessage should include("Das Board kann nur im InputBoardSize-Zustand erstellt werden.")
    }

    "makeMove sucessful" in {
      val controller = injector.getInstance(classOf[ControllerComponent])
      controller.changeState(GameState.InputBoardSize)
      controller.createNewBoard(8,8)
      controller.setGameMode("Ai")
      controller.changeState(GameState.InputPlayer1)
      controller.addPlayers("Imran")
      controller.changeState(GameState.WHITE_TURN)
      controller.processTurn(5,3)

      controller.getBoard.getStoneAt(5,3) should be (Stone.White)
      controller.getBoard.getStoneAt(4,3) should be (Stone.White)
    }
  }
}
