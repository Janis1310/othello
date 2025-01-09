package de.htwg.se.othello.aview.GUI
import scala.swing._
import scala.swing.event.ButtonClicked
import de.htwg.se.othello.model.BoardComponents.BoardBaseImpl.Stone
import scala.util.Try
import javax.swing.ImageIcon
import de.htwg.se.othello.util.Observer
import de.htwg.se.othello.controller.ControllerComponents.ControllerComponent

class OthelloGUI(controller: ControllerComponent)
    extends MainFrame
    with Observer {
  controller.add(this)
  title = "Othello"

  val white_stone = new ImageIcon("src/main/resources/pieces/white.png")
  val black_stone = new ImageIcon("src/main/resources/pieces/black.png")

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

    contents += new BoxPanel(Orientation.Vertical) {
      preferredSize = new Dimension(300, 20)

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

    val button = new Button("Start") {
      Alignment.Center

    }
    listenTo(button)

    contents += new BoxPanel(Orientation.Vertical) {
      contents += button
    }
    reactions += { case ButtonClicked(button) =>
      val player1 =
        if (player1Field.text.nonEmpty) player1Field.text else "Player 1"
      val player2 =
        if (player2Field.text.nonEmpty) player2Field.text else "Player 2"
      val rows = Try(rowsField.text.toInt).getOrElse(8)
      val cols = Try(colsField.text.toInt).getOrElse(8)

      if (rows > 0 && cols > 0) {
        controller.addPlayers(
          player1,
          player2
        ) // Spieler zum Controller hinzufügen
        controller.createNewBoard(rows, cols) // Neues Spielfeld erstellen
        println(
          s"Spiel gestartet mit $player1 und $player2 auf einem $rows x $cols Feld."
        )
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
    layout(new Label(s"${controller.getCurrentPlayer.name}'s Turn")) =
      BorderPanel.Position.North

    // Spielfeld als zentrales Element
    layout(
      new GridPanel(
        controller.getBoard.getBoard.numRows,
        controller.getBoard.getBoard.numCols
      ) {
        for (
          row <- 0 until controller.getBoard.getBoard.numRows;
          col <- 0 until controller.getBoard.getBoard.numCols
        ) {
          val cellValue =
            controller.getBoard.getBoard.cell(row, col) // Zelleninhalt abrufen
          val button = new Button {
            text = " " // Text bleibt leer
            preferredSize = new Dimension(50, 50)

            // Hintergrundfarbe je nach Zellenwert setzen
            cellValue match {
              case Stone.Empty =>
                background = new Color(65, 100, 40) // Leeres Feld
              case Stone.Black =>
                icon = black_stone;
                background = new Color(65, 100, 40) // Schwarzer Stein
              case Stone.White =>
                icon = white_stone;
                background = new Color(65, 100, 40) // Weißer Stein
            }
          }

          // Button-Event für einen Zug
          listenTo(button)
          reactions += { case ButtonClicked(`button`) =>
            val result = controller.makeMove(row, col)
            result match {
              case Right(_) =>
                // Spielfeld aktualisieren, ohne das gesamte Panel neu zu erstellen

                refreshBoard() // Oder setze den Inhalt des GridPanels direkt
              case Left(message) =>
                Dialog.showMessage(
                  this,
                  message,
                  "Ungültiger Zug",
                  Dialog.Message.Error
                )
            }
          }

          contents += button
        }
      }
    ) = BorderPanel.Position.Center
  }

  centerOnScreen() // Das Fenster wird zentriert
  visible = true

  def start(): Unit = {
    contents = createinitboard
  }

  def refreshBoard(): Unit = {
    contents = createboard
    validate() // Aktualisiert das Layout repaint() // Zeichnet die GUI neu
    repaint()
  }

  override def update: Unit = {
    println("Das Spielfeld wurde aktualisiert.")
    refreshBoard()
    println(controller.boardToString)

  }

}
