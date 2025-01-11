package de.htwg.se.othello.model.BoardComponents

/**
 * Das `BoardComponent`-Trait definiert die Schnittstelle für ein Spielfeld.
 *
 * Es bietet Methoden zum Abrufen des Spielfelds, der Anzahl der Zeilen und Spalten,
 * zum Platzieren eines Steins und zum Erstellen einer Kopie des Spielfelds.
 */
trait BoardComponent {
  /**
   * Gibt die aktuelle Matrix des Spielfelds zurück.
   *
   * @return Eine Matrix, die die Steine auf dem Spielfeld repräsentiert.
   */
  def getBoard: MatrixInterface[StoneComponent]

  /**
   * Gibt die Anzahl der Zeilen des Spielfelds zurück.
   *
   * @return Die Anzahl der Zeilen.
   */
  def numRows: Int

  /**
   * Gibt die Anzahl der Spalten des Spielfelds zurück.
   *
   * @return Die Anzahl der Spalten.
   */
  def numCols: Int

  /**
   * Platziert einen Stein auf dem Spielfeld an der angegebenen Position.
   *
   * @param x Die X-Koordinate der Position.
   * @param y Die Y-Koordinate der Position.
   * @param stone Der Stein, der platziert werden soll.
   * @return Ein neues `BoardComponent` mit dem platzierten Stein.
   */
  def placeStone(x: Int, y: Int, stone: StoneComponent): BoardComponent

  /**
   * Erstellt eine Kopie des aktuellen Spielfelds.
   *
   * @return Eine Kopie des Spielfelds.
   */
  def copy(): BoardComponent
  override def toString: String
}