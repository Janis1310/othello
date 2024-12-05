package de.htwg.se.othello.model
import de.htwg.se.othello.controller.Controller

trait Command {
    def doStep:Unit
    def undoStep:Unit
    def redoStep:Unit
}

class SetCommand(board:Board, exBoard : Board, stone:Stone, controller: Controller) extends Command {
  override def doStep: Unit = controller.board = board// hier neuen Board und Alten zustand übergeben, Contoller die Instanzvariable übergeben. 

  override def undoStep: Unit = controller.board = exBoard

  override def redoStep: Unit = controller.board = board
}
