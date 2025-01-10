package de.htwg.se.othello.model

import de.htwg.se.othello.model.CommandComponents.CommandBaseImpl.{
  Command,
  UndoManager
}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class UndoManagerSpec extends AnyWordSpec with Matchers {
  class TestCommand extends Command {
    var executed: Boolean = false

    override def doStep: Unit = executed = true
    override def undoStep: Unit = executed = false
    override def redoStep: Unit = executed = true
  }

  "An UndoManager" should {

    "execute a command and add it to the undo stack on doStep" in {
      val command = new TestCommand
      val undoManager = new UndoManager

      undoManager.doStep(command)

      command.executed should be(true) // Command should have been executed
    }

    "undo the last command and add it to the redo stack" in {
      val command = new TestCommand
      val undoManager = new UndoManager

      undoManager.doStep(command)
      undoManager.undoStep()

      command.executed should be(false) // Command should have been undone
    }

    "redo the last undone command and add it back to the undo stack" in {
      val command = new TestCommand
      val undoManager = new UndoManager

      undoManager.doStep(command)
      undoManager.undoStep()
      undoManager.redoStep()

      command.executed should be(true) // Command should have been redone
    }

    "do nothing when undo is called on an empty undo stack" in {
      val undoManager = new UndoManager
      noException should be thrownBy undoManager
        .undoStep() // Should not throw an exception
    }

    "do nothing when redo is called on an empty redo stack" in {
      val undoManager = new UndoManager
      noException should be thrownBy undoManager
        .redoStep() // Should not throw an exception
    }
  }

}
