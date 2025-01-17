package de.htwg.se.othello.controller.ControllerComponents

import scala.swing.event.Event

// Event für eine Zelländerung
class CellChanged(val row: Int, val col: Int) extends Event

// Event für eine Änderung der Feldgröße (z. B. beim Erstellen eines neuen Spielfelds)
case class GridSizeChanged(newRows: Int, newCols: Int) extends Event

// Event für Änderungen an den möglichen Zügen (z. B. bei einem Zug)
class CandidatesChanged extends Event
