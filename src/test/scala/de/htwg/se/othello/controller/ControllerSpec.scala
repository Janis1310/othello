package de.htwg.se.othello.controller

import de.htwg.se.othello.Othello
import de.htwg.se.othello.Othello.injector
import de.htwg.se.othello.model.BoardComponents.BoardBaseImpl.{Board, Stone}

import scala.collection.immutable.Queue
import de.htwg.se.othello.controller.ControllerComponents.{ControllerComponent, GameState}
import de.htwg.se.othello.model.BoardComponents.BoardComponent
import de.htwg.se.othello.model.HandlerComponents.HandlerBaseImpl.MoveHandler
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
      controller.createNewBoard(8, 8)
      controller.getBoard shouldBe new Board(8, 8)
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
      controller.createNewBoard(8, 8)
      controller.setGameMode("Ai")
      controller.changeState(GameState.InputPlayer1)
      controller.addPlayers("Imran")
      controller.changeState(GameState.WHITE_TURN)
      controller.makeMove(5, 3)
      controller.makeMove(5,4)

      controller.getBoard.getStoneAt(5, 3) should be(Stone.White)
      controller.getBoard.getStoneAt(4, 3) should be(Stone.White)
      controller.getBoard.getStoneAt(5, 4) should be(Stone.Black)
    }
    "makeMove fail" in {
      val controller = injector.getInstance(classOf[ControllerComponent])
      controller.changeState(GameState.InputBoardSize)
      controller.createNewBoard(8, 8)
      controller.setGameMode("Ai")
      controller.changeState(GameState.InputPlayer1)
      controller.addPlayers("Imran")
      controller.changeState(GameState.WHITE_TURN)
      controller.makeMove(9, 3) shouldBe false

      controller.changeState(GameState.SETUP)
      intercept[IllegalStateException] {
        controller.makeMove(5, 3)
      }.getMessage should include {"Züge sind nur während eines Spielzugs erlaubt."}
    }

    "next player only in right state" in {
      val controller = injector.getInstance(classOf[ControllerComponent])
      controller.setGameMode("Human")


      controller.changeState(GameState.InputPlayer1)
      controller.addPlayers("Alex")
      controller.changeState(GameState.InputPlayer2)
      controller.addPlayers("Pascal")
      controller.changeState(GameState.InputBoardSize)
      intercept[IllegalStateException] {
        controller.nextPlayer()
      }.getMessage should include("Spielerwechsel ist im aktuellen Zustand nicht möglich.")
    }
    "next player when no move is possible" in {
      val controller = injector.getInstance(classOf[ControllerComponent])
      controller.setGameMode("Human")
      controller.changeState(GameState.InputPlayer1)
      controller.addPlayers("Alex")
      controller.changeState(GameState.InputPlayer2)
      controller.addPlayers("Pascal")
      controller.changeState(GameState.InputBoardSize)
      controller.createNewBoard(8,8)
      controller.changeState(GameState.WHITE_TURN)
      val board = new Board(8,8)
      val updatedBoard = board
        .placeStone(4,3,Stone.White)
        .placeStone(3,4,Stone.White)
        .placeStone(7,7,Stone.White)
        .placeStone(6,7,Stone.Black)
      controller.setBoard(updatedBoard)
      controller.nextPlayer()
      println(MoveHandler.isAnyMovePossible(controller.getBoard, Stone.BlackStone))
    }

    "processAiTurn" in {
      val controller = injector.getInstance(classOf[ControllerComponent])
      controller.changeState(GameState.InputBoardSize)
      controller.createNewBoard(8, 8)
      controller.setGameMode("Ai")
      controller.changeState(GameState.InputPlayer1)
      controller.addPlayers("Domminik")
      controller.changeState(GameState.BLACK_TURN)
      controller.processAITurn() shouldBe true

    }
    "processAiTurn = false, full board" in {
      val controller = injector.getInstance(classOf[ControllerComponent])
      controller.changeState(GameState.InputBoardSize)
      controller.createNewBoard(8, 8)
      controller.setGameMode("Ai")
      controller.changeState(GameState.InputPlayer1)
      controller.addPlayers("Jake")
      controller.changeState(GameState.WHITE_TURN)
      controller.makeMove(5,3)
      controller.nextPlayer()
      controller.makeMove(2,4)
      controller.processAITurn() shouldBe false


    }


    "getGameMode" in {
      val controller = injector.getInstance(classOf[ControllerComponent])
      controller.setGameMode("Human")
      controller.getGameMode shouldBe ("Human")
    }
  }
  "undo and redo" in {
    val controller = injector.getInstance(classOf[ControllerComponent])
    controller.changeState(GameState.InputBoardSize)
    controller.createNewBoard(8, 8)
    controller.setGameMode("Ai")
    controller.changeState(GameState.InputPlayer1)
    controller.addPlayers("Philippe")
    controller.changeState(GameState.WHITE_TURN)
    val initialBoard = controller.getBoard.copy()

    controller.makeMove(5, 3)
    val boardAfterMove = controller.getBoard.copy()
    controller.undo
    controller.getBoard should equal(initialBoard)
    controller.getGameState shouldBe(GameState.WHITE_TURN)
    controller.redo
    controller.getGameState shouldBe (GameState.BLACK_TURN)
    controller.getBoard should equal(boardAfterMove)
  }
  "boardToString" in {
    val controller = injector.getInstance(classOf[ControllerComponent])
    val initialBoardStr =
      """
          0   1   2   3   4   5   6   7
        +---+---+---+---+---+---+---+---+
      0 | . | . | . | . | . | . | . | . |
        +---+---+---+---+---+---+---+---+
      1 | . | . | . | . | . | . | . | . |
        +---+---+---+---+---+---+---+---+
      2 | . | . | . | . | . | . | . | . |
        +---+---+---+---+---+---+---+---+
      3 | . | . | . | W | S | . | . | . |
        +---+---+---+---+---+---+---+---+
      4 | . | . | . | S | W | . | . | . |
        +---+---+---+---+---+---+---+---+
      5 | . | . | . | . | . | . | . | . |
        +---+---+---+---+---+---+---+---+
      6 | . | . | . | . | . | . | . | . |
        +---+---+---+---+---+---+---+---+
      7 | . | . | . | . | . | . | . | . |
        +---+---+---+---+---+---+---+---+ """

    controller.changeState(GameState.InputBoardSize)
    controller.createNewBoard(8, 8)
    controller.setGameMode("Ai")
    controller.changeState(GameState.InputPlayer1)
    controller.addPlayers("Miréio")
    controller.changeState(GameState.WHITE_TURN)

    controller.boardToString.replaceAll("\\s+", "") shouldBe initialBoardStr
      .replaceAll("\\s+", "")
  }
}
