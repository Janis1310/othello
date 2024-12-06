package de.htwg.se.othello.model


class UndoManager {
  private var undoStack: List[Command]= Nil // Stack of runed commands
  private var redoStack: List[Command]= Nil // Stack der rückgängig gemachten Befehle
  def doStep(command: Command) = { // führt neuen Befehl aus
    println("doStep!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
    undoStack = command::undoStack // add command to undoStack
    command.doStep
  }
  def undoStep  = {
    undoStack match {
      case  Nil => // Stack is empty
      case head::stack => {
        head.undoStep
        undoStack=stack
        redoStack= head::redoStack // add undone command to redoStack
      }
    }
  }
  def redoStep = {
    redoStack match {
      case Nil =>
      case head::stack => {
        head.redoStep
        redoStack=stack
        undoStack=head::undoStack
      }
    }
  }
}