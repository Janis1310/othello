package de.htwg.se.othello.controller

object GameState extends Enumeration {
  type GameState = Value

  // Zustände des Spiels
  val SETUP, WHITE_TURN, BLACK_TURN, GAME_OVER, PAUSED = Value

  // Nachrichten oder Aktionen für jeden Zustand
  private val stateMessages = Map[GameState, String](
    SETUP -> "Das Spiel wird vorbereitet.",
    WHITE_TURN -> "GameState: White.",
    BLACK_TURN -> "GameState: Black.",
    GAME_OVER -> "GameState: Game Over",
    PAUSED -> "GameState: Game Paused"
  )

  private val stateActions = Map[GameState, () => Unit](
    SETUP -> (() => println("Setup: Spieler hinzufügen und Board konfigurieren.")),
    WHITE_TURN -> (() => println("Weiß macht einen Zug.")),
    BLACK_TURN -> (() => println("Schwarz macht einen Zug.")),
    GAME_OVER -> (() => println("Spielstand anzeigen und beenden.")),
    PAUSED -> (() => println("Spiel pausieren."))
  )

  // Zugriff auf die Nachricht für einen Zustand
  def message(state: GameState): String = stateMessages.getOrElse(state, "Unbekannter Zustand.")

  // Zugriff auf die Aktion für einen Zustand
  def action(state: GameState): Unit = stateActions.getOrElse(state, () => println("Keine Aktion definiert."))()
}