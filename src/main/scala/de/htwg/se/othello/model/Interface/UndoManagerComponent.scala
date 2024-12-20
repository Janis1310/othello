package de.htwg.se.othello.model.Interface

import de.htwg.se.othello.model.Command

trait UndoManagerComponent {
  // Führe einen neuen Befehl aus
  def doStep(command: Command): Unit
  
  // Mache den letzten Befehl rückgängig
  def undoStep(): Unit
  
  // Wiederhole den letzten rückgängig gemachten Befehl
  def redoStep(): Unit
}