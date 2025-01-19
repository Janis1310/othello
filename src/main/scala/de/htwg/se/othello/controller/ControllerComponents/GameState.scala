package de.htwg.se.othello.controller.ControllerComponents

object GameState extends Enumeration {
  type GameState = Value

  // Zustände des Spiels
  val SETUP, WHITE_TURN, BLACK_TURN, GAME_OVER, PAUSED, InputPlayer1, InputPlayer2, InputBoardSize = Value

  // Nachrichten oder Aktionen für jeden Zustand
  private val stateMessages = Map[GameState, String](
    SETUP -> "Das Spiel wird vorbereitet.",
    WHITE_TURN -> "GameState: White.",
    BLACK_TURN -> "GameState: Black.",
    GAME_OVER -> "GameState: Game Over",
    PAUSED -> "GameState: Game Paused",
    InputPlayer1 -> "Bitte geben Sie den Namen von Spieler 1 ein.",
    InputPlayer2 -> "Bitte geben Sie den Namen von Spieler 2 ein.",
    InputBoardSize -> "Bitte geben Sie die Größe des Spielbretts ein (Format: Zeilen,Spalten)."
  )

  private val stateActions = Map[GameState, () => Unit](
  SETUP -> (() => println("Setup: Spieler hinzufügen und Board konfigurieren.")),
  
  WHITE_TURN -> (() => println("Weiß")),
  
  BLACK_TURN -> (() => println("Schwarz")),
  
  GAME_OVER -> (() => println("Das Spiel ist zu ende. Es gibt keine legalen Züge mehr.")),
  
  PAUSED -> (() => println("Spiel pausiert.")),
  
  InputPlayer1 -> (() => println("Bitte geben Sie den Namen von Spieler 1 ein:")),
  
  InputPlayer2 -> (() => println("Bitte geben Sie den Namen von Spieler 2 ein:")),
  
  InputBoardSize -> (() => println("Bitte geben Sie die Größe des Spielbretts ein (Format: Zeilen,Spalten):"))
  )

  // Zugriff auf die Nachricht für einen Zustand
  def message(state: GameState): String =
    stateMessages.getOrElse(state, "Unbekannter Zustand.")

  // Zugriff auf die Aktion für einen Zustand
  def action(state: GameState): Unit =
    stateActions.getOrElse(state, () => println("Keine Aktion definiert."))()
}
