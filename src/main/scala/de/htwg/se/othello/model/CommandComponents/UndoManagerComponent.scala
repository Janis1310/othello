package de.htwg.se.othello.model.CommandComponents

import de.htwg.se.othello.model.CommandComponents.CommandBaseImpl.Command

trait UndoManagerComponent {
  // Führe einen neuen Befehl aus
  def doStep(command: Command): Unit

  // Mache den letzten Befehl rückgängig
  def undoStep(): Unit

  // Wiederhole den letzten rückgängig gemachten Befehl
  def redoStep(): Unit
}
