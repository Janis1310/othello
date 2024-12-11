package de.htwg.se.othello.aview.GUI

import scala.swing._
import scala.swing.event.ButtonClicked
import de.htwg.se.othello.controller.Controller

class OthelloGUI(controller:Controller) extends MainFrame{
  title = "Othello"

  // Spielfeldgröße
  val rowsField = new TextField { columns = 5 }
  val colsField = new TextField { columns = 5 }

  // Spielernamen
  val player1Field = new TextField { columns = 15 }
  val player2Field = new TextField { columns = 15 }

  // Start-Button
  val startButton = new Button("Start")

   // Die Methode, um das Initialisierungs-Panel zu erstellen
  def createInitPanel = new BoxPanel(Orientation.Vertical) {
    // Hinzufügen eines Panels für die Eingabe der Spielernamen
    contents += new BoxPanel(Orientation.Horizontal) {
      contents += new Label("Player 1:")
      contents += player1Field
      contents += new Label("Player 2:")
      contents += player2Field

    }

    // Platzhalter für Abstände
    contents += new BoxPanel(Orientation.Vertical) {
      preferredSize = new Dimension(300, 20)

    }

    // Hinzufügen eines Panels für die Eingabe der Spielfeldgröße
    contents += new BoxPanel(Orientation.Horizontal) {
      contents += new Label("Rows:")
      contents += rowsField
      contents += new Label("Columns:")
      contents += colsField
    }
    // Startbutton hinzufügen
    contents += startButton
  }

  def start(): Unit = {
    // Inhalte setzen
    contents = createInitPanel

    listenTo(startButton)
    reactions += {
      case ButtonClicked(startButton) =>
        val player1Name = player1Field.text.trim
        val player2Name = player2Field.text.trim
        val rows = rowsField.text.trim
        val cols = colsField.text.trim

        if (player1Name.nonEmpty && player2Name.nonEmpty) {
          controller.addPlayers(player1Name, player2Name)
        } else {
          Dialog.showMessage(this, "Bitte beide Spielernamen eingeben.", "Fehler", Dialog.Message.Error)
        }

        if (player1Name.isEmpty || player2Name.isEmpty) {
          Dialog.showMessage(this, "Bitte beide Spielernamen eingeben.", "Fehler", Dialog.Message.Error)
        } else if (!rows.forall(_.isDigit) || !cols.forall(_.isDigit)) {
          Dialog.showMessage(this, "Bitte eine gültige Spielfeldgröße eingeben.", "Fehler", Dialog.Message.Error)
        } else {
          // Übergabe der Eingaben an den Controller
          controller.addPlayers(player1Name, player2Name)
          controller.createNewBoard(rows.toInt, cols.toInt)

          Dialog.showMessage(this, "Spiel gestartet!", "Info", Dialog.Message.Info)
        }
    }

    centerOnScreen() // Das Fenster wird zentriert
    visible = true

  }


}

  

