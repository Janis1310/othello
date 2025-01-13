package de.htwg.se.othello.controller

import de.htwg.se.othello.Othello
import de.htwg.se.othello.Othello.injector
import de.htwg.se.othello.model.BoardComponents.BoardBaseImpl.{Board, Stone}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import scala.collection.immutable.Queue
import de.htwg.se.othello.util.Observer
import de.htwg.se.othello.controller.ControllerComponents.ControllerBaseImpl.Controller
import de.htwg.se.othello.controller.ControllerComponents.{ControllerComponent, GameState}
import de.htwg.se.othello.model.BoardComponents.BoardBaseImpl.Board
import de.htwg.se.othello.model.BoardComponents.BoardComponent
import de.htwg.se.othello.model.CommandComponents.{CommandBaseImpl, UndoManagerComponent}
import de.htwg.se.othello.model.HandlerComponents.MoveHandlerTemplateInterface
import de.htwg.se.othello.model.Playercomponents.Player
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class ControllerSpec extends AnyWordSpec with Matchers {
  val controller = injector.getInstance(classOf[ControllerComponent])
  "A Controller" should {
    "change and get gameState" in {
      controller.changeState(GameState.SETUP)
      controller.getGameState shouldBe GameState.SETUP
    }

    "add player1 game State: Ai" in {
      controller.setGameMode("Ai")
      controller.changeState(GameState.InputPlayer1)
      controller.addPlayers("Imran")
      controller.getPlayers shouldBe Queue(Player("Imran", Stone.White, "Human"), Player("AI", Stone.Black, "AI"))
    }
    "add player 1 and 2 game State: Human" in {
      val controller1 = injector.getInstance(classOf[ControllerComponent])
      controller1.setGameMode("Human")
      controller1.changeState(GameState.InputPlayer1)
      controller1.addPlayers("Janis")
      controller1.addPlayers("Imran")
      controller1.getPlayers shouldBe Queue(Player("Janis", Stone.White, "Human"), Player("Imran", Stone.Black, "Human"))
    }

    "add player not successful" in {
      val controller2 = injector.getInstance(classOf[ControllerComponent])
      controller2.changeState(GameState.BLACK_TURN)
      intercept[IllegalStateException] {
        controller2.addPlayers("Imran")
      }.getMessage should include("Spieler können nur im InputPlayer Zustand hinzugefügt werden")

    }

    /*"add player1 gameState: Ai " in {
      controller.changeState(GameState.InputPlayer1)
      controller.addPlayers("Janis")
      controller.getPlayers shouldBe Queue(Player("Imran", Stone.White, "Human"), Player("Janis", Stone.White, "Ai"))
    }*/




  }
}
