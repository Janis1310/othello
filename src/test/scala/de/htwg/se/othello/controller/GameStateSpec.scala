package de.htwg.se.othello.controller

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class GameStateSpec extends AnyWordSpec with Matchers {

  "The GameState object" should {

    "return the correct message for each state" in {
      GameState.message(GameState.SETUP) should be ("Das Spiel wird vorbereitet.")
      GameState.message(GameState.WHITE_TURN) should be ("GameState: White.")
      GameState.message(GameState.BLACK_TURN) should be ("GameState: Black.")
      GameState.message(GameState.GAME_OVER) should be ("GameState: Game Over")
      GameState.message(GameState.PAUSED) should be ("GameState: Game Paused")
    }
  }
}