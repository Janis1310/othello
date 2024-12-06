package de.htwg.se.othello.model
import de.htwg.se.othello.controller.Controller

trait Command {
    def doStep:Unit
    def undoStep:Unit
    def redoStep:Unit
}

class SetCommand(private val previousBoard: Board, private val newBoard: Board, private val controller: Controller) extends Command {

  override def doStep: Unit = {
    // Setze das Board im Controller auf den neuen Zustand
    controller.setBoard(newBoard)
  }

  override def undoStep: Unit = {
    // Setze das Board im Controller auf den vorherigen Zustand zurück
    controller.setBoard(previousBoard)
  }

  override def redoStep: Unit = {
    // Wiederhole den Schritt, indem das Board auf den neuen Zustand gesetzt wird
    controller.setBoard(newBoard)
  }
}

