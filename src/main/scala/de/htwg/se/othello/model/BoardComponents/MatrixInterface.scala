package de.htwg.se.othello.model.BoardComponents

/**
 * Ein generisches Interface zur Darstellung einer Matrixstruktur.
 *
 * @tparam T Der Typ der Elemente, die in der Matrix gespeichert werden.
 */
trait MatrixInterface[T] {

  def numRows: Int // Number of rows
  def numCols: Int // Number of columns

  /**
   * Gibt den Wert einer Zelle an einer bestimmten Position in der Matrix zurück.
   *
   * @param row Die Zeilennummer (0-basiert).
   * @param col Die Spaltennummer (0-basiert).
   * @return Der Wert in der angegebenen Zelle.
   */
  def cell(row: Int, col: Int): T

  /**
   * Erstellt eine neue Matrix, die vollständig mit einem bestimmten Wert gefüllt ist.
   *
   * @param filling Der Wert, mit dem die Matrix gefüllt werden soll.
   * @return Eine neue Matrix mit dem angegebenen Füllwert.
   */
  def fill(filling: T): MatrixInterface[T]
  /**
   * Erstellt eine neue Matrix mit einer ersetzten Zelle an der angegebenen Position.
   *
   * @param row Die Zeilennummer der zu ersetzenden Zelle (0-basiert).
   * @param col Die Spaltennummer der zu ersetzenden Zelle (0-basiert).
   * @param cell Der neue Wert für die Zelle.
   * @return Eine neue Matrix mit der aktualisierten Zelle.
   */
  def replaceCell(row: Int, col: Int, cell: T): MatrixInterface[T]
}
