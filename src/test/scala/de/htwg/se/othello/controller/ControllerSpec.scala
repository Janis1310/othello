package de.htwg.se.othello.controller

import de.htwg.se.othello.model.BoardComponents.BoardBaseImpl.{Board, Stone}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import scala.collection.immutable.Queue
import de.htwg.se.othello.util.Observer

import de.htwg.se.othello.controller.ControllerComponents.ControllerBaseImpl.Controller
import de.htwg.se.othello.controller.ControllerComponents.GameState
import de.htwg.se.othello.model.BoardComponents.BoardBaseImpl.Board
import de.htwg.se.othello.model.BoardComponents.BoardComponent
import de.htwg.se.othello.model.CommandComponents.{CommandBaseImpl, UndoManagerComponent}
import de.htwg.se.othello.model.HandlerComponents.MoveHandlerTemplateInterface
import de.htwg.se.othello.model.Playercomponents.Player
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class ControllerSpec extends AnyWordSpec with Matchers {

  /*"A Controller" should {
    val mockBoard: BoardComponent = new Board(8, 8)
    val undoManager: UndoManagerComponent = new UndoManagerComponent {
      override def doStep(command: CommandBaseImpl.Command): Unit = {}
      override def redoStep(): Unit = {}
      override def undoStep(): Unit = {}
    }
    val moveHandler: MoveHandlerTemplateInterface = new MoveHandlerTemplateInterface {
      override def processMove(stonePosition: de.htwg.se.othello.model.BoardComponents.BoardBaseImpl.Stoneposition, board: BoardComponent): BoardComponent = {
        board // Simulate processing the move by returning the board as is
      }
    }

    val controller = new Controller(mockBoard, undoManager, moveHandler)

    "initialize players in SETUP state" in {
      controller.getGameState shouldBe GameState.SETUP

      controller.addPlayers("Player1", "Player2")
      val players = controller.getPlayers
      players.size shouldBe 2
      players.head.name shouldBe "Player1"
      players.last.name shouldBe "Player2"
    }

    "not allow player addition outside SETUP state" in {
      controller.changeState(GameState.WHITE_TURN)
      controller.addPlayers("Player1", "Player2")

      val players = controller.getPlayers
      players shouldBe empty
    }

    "cycle through players on nextPlayer call" in {
      controller.changeState(GameState.SETUP)
      controller.addPlayers("Player1", "Player2")
      controller.changeState(GameState.WHITE_TURN)

      val initialPlayer = controller.getCurrentPlayer
      controller.nextPlayer()
      val nextPlayer = controller.getCurrentPlayer

      initialPlayer should not be nextPlayer
      initialPlayer.name shouldBe "Player1"
      nextPlayer.name shouldBe "Player2"
    }

    "create a new board in SETUP state" in {
      controller.changeState(GameState.SETUP)

      val rows = 8
      val cols = 8
      val newBoard = controller.createNewBoard(rows, cols)

      newBoard shouldBe a [Board]
    }

    "not create a board outside SETUP state" in {
      controller.changeState(GameState.WHITE_TURN)
      val initialBoard = controller.getBoard

      controller.createNewBoard(8, 8)
      val currentBoard = controller.getBoard

      currentBoard shouldBe initialBoard
    }

    "handle valid and invalid moves correctly" in {
      controller.changeState(GameState.WHITE_TURN)

      val result = controller.makeMove(3, 3)
      result shouldBe true
    }

    "allow undo and redo functionality" in {
      controller.undo
      // Assuming undo logic works without exception

      controller.redo
      // Assuming redo logic works without exception
    }

    "switch to AI turn if current player is AI" in {
      controller.changeState(GameState.SETUP)
      val aiPlayer = Player("AI_Player", de.htwg.se.othello.model.BoardComponents.BoardBaseImpl.Stone.White, "AI")
      val humanPlayer = Player("Human_Player", de.htwg.se.othello.model.BoardComponents.BoardBaseImpl.Stone.Black, "Human")

      controller.addPlayers(aiPlayer.name, humanPlayer.name)
      controller.changeState(GameState.WHITE_TURN)

      val moveMade = controller.processTurn(3, 3)
      moveMade shouldBe true
    }
  }*/
}
