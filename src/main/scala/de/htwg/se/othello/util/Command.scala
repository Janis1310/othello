package de.htwg.se.othello.util

trait Command {
    def doStep:Unit
    def undoStep:Unit
    def redoStep:Unit
}
