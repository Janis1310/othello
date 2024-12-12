package de.htwg.se.othello.aview.GUI

import scala.swing._
import scala.swing.event.ButtonClicked
import de.htwg.se.othello.controller.Controller
import scalafx.scene.shape.StrokeLineCap.Butt
import scala.util.Try
import de.htwg.se.othello.model.Stone

class OthelloGUI(controller:Controller) extends MainFrame{
  title = "Othello"

   // Die Methode, um das Initialisierungs-Panel zu erstellen
  def createinitboard = new BoxPanel(Orientation.Vertical) {
    val player1Field = new TextField { columns = 15 }
    val player2Field = new TextField { columns = 15 }

    contents += new BoxPanel(Orientation.Horizontal) {
      
      contents += new Label("Player 1:")
      contents += player1Field
      contents += new Label("Player 2:")
      contents += player2Field
    
    }

    contents += new BoxPanel(Orientation.Vertical){
       preferredSize = new Dimension(300,20)

    }
    val rowsField = new TextField { columns = 5 }
    val colsField = new TextField { columns = 5 }

    // Hinzufügen eines Panels für die Eingabe der Spielfeldgröße
    contents += new BoxPanel(Orientation.Horizontal) {
      
      contents += new Label("Rows:")
      contents += rowsField
      contents += new Label("Columns:")
      contents += colsField
    }

    val button = new Button("Start"){
      Alignment.Center
    }
    listenTo(button)

    contents += new BoxPanel(Orientation.Vertical){
    contents += button
  }
    reactions +={
      case ButtonClicked(button) =>
        val player1 = player1Field.text
        val player2 = player2Field.text
        val rows = Try(rowsField.text.toInt).getOrElse(-1) // Fehlerhafte Eingabe wird zu -1
        val cols = Try(colsField.text.toInt).getOrElse(-1)

        if (rows > 0 && cols > 0) {
          controller.addPlayers(player1, player2) // Spieler zum Controller hinzufügen
          controller.createNewBoard(rows, cols) // Neues Spielfeld erstellen
          println(s"Spiel gestartet mit $player1 und $player2 auf einem $rows x $cols Feld.")
          refreshBoard()
          
          // Hier könntest du das Spielfeld anzeigen lassen
        } else {
          Dialog.showMessage(
            this,
            "Bitte geben Sie gültige Werte für die Anzahl der Zeilen und Spalten ein.",
            "Ungültige Eingabe",
            Dialog.Message.Error
          )
        }
        
    }

  }

    def createboard: BorderPanel = new BorderPanel {
        // Obere Statusanzeige
        layout(new Label(s"${controller.getCurrentPlayer.name}'s Turn")) = BorderPanel.Position.North

        // Spielfeld als zentrales Element
        layout(new GridPanel(controller.board.getBoard.numRows, controller.board.getBoard.numCols) {
          for (row <- 0 until controller.board.getBoard.numRows; col <- 0 until controller.board.getBoard.numCols) {
            val cellValue = controller.board.getBoard.cell(row, col) // Zelleninhalt abrufen
            val button = new Button {
              text = " " // Text bleibt leer
              preferredSize = new Dimension(50, 50)

              // Hintergrundfarbe je nach Zellenwert setzen
              cellValue match {
                case Stone.Empty => background = java.awt.Color.GREEN // Leeres Feld
                case Stone.Black => background = java.awt.Color.BLACK // Schwarzer Stein
                case Stone.White => background = java.awt.Color.WHITE // Weißer Stein
              }
            }

            // Button-Event für einen Zug
            listenTo(button)
            reactions += {
              case ButtonClicked(`button`) =>
                val result = controller.makeMove(row, col)
                result match {
                  case Right(_) =>
                    // Spielfeld aktualisieren, ohne das gesamte Panel neu zu erstellen

                    refreshBoard() // Oder setze den Inhalt des GridPanels direkt
                  case Left(message) =>
                    Dialog.showMessage(this, message, "Ungültiger Zug", Dialog.Message.Error)
                }
            }

            contents += button
          }
        }) = BorderPanel.Position.Center
      }
  
  centerOnScreen()  // Das Fenster wird zentriert
  visible = true

  def start(): Unit = {
    contents = createinitboard
  }

  def refreshBoard(): Unit = { 
    contents = createboard 
    validate() // Aktualisiert das Layout repaint() // Zeichnet die GUI neu
    repaint()
}

}

  

