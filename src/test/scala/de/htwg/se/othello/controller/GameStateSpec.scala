package de.htwg.se.othello.controller

import de.htwg.se.othello.controller.ControllerComponents.GameState
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se.othello.controller.ControllerComponents.GameState.*

class GameStateSpec extends AnyWordSpec with Matchers {

  "The GameState object" should {

    "provide the correct message for each state" in {
      GameState.message(SETUP) should be("Das Spiel wird vorbereitet.")
      GameState.message(WHITE_TURN) should be("GameState: White.")
      GameState.message(BLACK_TURN) should be("GameState: Black.")
      GameState.message(GAME_OVER) should be("GameState: Game Over")
      GameState.message(PAUSED) should be("GameState: Game Paused")
    }

    "return a default message for an unknown state" in {
      GameState.message(null.asInstanceOf[GameState]) should be(
        "Unbekannter Zustand."
      )
    }

    "execute the correct action for each state" in {
      val outStream = new java.io.ByteArrayOutputStream()
      Console.withOut(outStream) {
        GameState.action(SETUP)
        GameState.action(WHITE_TURN)
        GameState.action(BLACK_TURN)
        GameState.action(GAME_OVER)
        GameState.action(PAUSED)
        GameState.action(InputPlayer1)
        GameState.action(InputPlayer2)
        GameState.action(InputBoardSize)
      }

      val output = outStream.toString
      output should include("Setup: Spieler hinzufügen und Board konfigurieren.")
      output should include("Weiß")
      output should include("Schwarz")
      output should include("Spielstand anzeigen und beenden.")
      output should include("Spiel pausiert.")
      output should include("Bitte geben Sie den Namen von Spieler 1 ein:")
      output should include("Bitte geben Sie den Namen von Spieler 2 ein:")
      output should include("Bitte geben Sie die Größe des Spielbretts ein (Format: Zeilen,Spalten):")
    }

    "execute the default action for an unknown state" in {
      val outStream = new java.io.ByteArrayOutputStream()
      Console.withOut(outStream) {
        action(null.asInstanceOf[GameState])
      }

      val output = outStream.toString
      output.trim should be("Keine Aktion definiert.")
    }

  }
}
