package de.htwg.se.othello.model.CommandComponents.CommandBaseImpl

import de.htwg.se.othello.model.CommandComponents.CommandBaseImpl.Command
import de.htwg.se.othello.model.CommandComponents.UndoManagerComponent


class UndoManager extends UndoManagerComponent{
  private var undoStack: List[Command]= Nil // Stack of runed commands
  private var redoStack: List[Command]= Nil // Stack der rückgängig gemachten Befehle

  override def doStep(command: Command): Unit = {
    undoStack = command :: undoStack // Füge den Befehl zum undoStack hinzu
    command.doStep // Führe den Befehl aus
  }

// Macht den letzten Befehl rückgängig und fügt ihn zum redoStack hinzu
  override def undoStep(): Unit = {
    undoStack match {
      case Nil => // Stack ist leer, nichts zu tun
      case head :: stack =>
        head.undoStep // Mache den Befehl rückgängig
        undoStack = stack // Entferne den Befehl aus dem undoStack
        redoStack = head :: redoStack // Füge den rückgängig gemachten Befehl zum redoStack hinzu
    }
  }

  override def redoStep(): Unit = {
    redoStack match {
      case Nil => // Redo-Stack ist leer, nichts zu tun
      case head :: stack =>
        head.redoStep // Wiederhole den rückgängig gemachten Befehl
        redoStack = stack // Entferne den Befehl aus dem redoStack
        undoStack = head :: undoStack // Füge den wiederholten Befehl zum undoStack hinzu
    }
  }
}