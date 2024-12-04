package de.htwg.se.othello.util

trait Command {
    def doStep:Unit
    def undoStep:Unit
    def redoStep:Unit
}

class SetCommand(row:Int, col: Int, value:Int, controller: Controller) extends Command {
 override def doStep: Unit =   controller.grid = controller.grid.set(row, col, value)
 override def undoStep: Unit = controller.grid = controller.grid.set(row, col, 0)
 override def redoStep: Unit = controller.grid = controller.grid.set(row, col, value)
}
