package de.htwg.se.othello.model.CommandComponents.CommandBaseImpl

import de.htwg.se.othello.model.CommandComponents.CommandBaseImpl.Command
import de.htwg.se.othello.model.CommandComponents.UndoManagerComponent
import com.google.inject.Inject

class UndoManager @Inject extends UndoManagerComponent {
  private var undoStack: List[Command] = Nil
  private var redoStack: List[Command] = Nil

  override def doStep(command: Command): Unit = {
    undoStack = command :: undoStack
    command.doStep
  }

  override def undoStep(): Unit = {
    undoStack match {
      case Nil =>
      case head :: stack =>
        head.undoStep
        undoStack = stack
        redoStack = head :: redoStack
    }
  }

  override def redoStep(): Unit = {
    redoStack match {
      case Nil =>
      case head :: stack =>
        head.redoStep
        redoStack = stack
        undoStack =
          head :: undoStack 
    }
  }
}
