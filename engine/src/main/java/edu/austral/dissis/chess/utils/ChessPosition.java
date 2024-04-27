package edu.austral.dissis.chess.utils;

import java.util.Objects;

public class ChessPosition {
  /** X,Y coordinates encapsulation. Try to do it one-based */
  private final int row;

  private final int column;

  public ChessPosition(int row, int column) {
    this.row = row;
    this.column = column;
  }

  public int getColumn() {
    return column;
  }

  public int getRow() {
    return row;
  }

  // More understandable
  @Override
  public String toString() {
    return "(" + row + "," + column + ')';
  }

  // Easier map getting
  @Override
  public boolean equals(Object obj) {
    if (obj == null || obj.getClass() != ChessPosition.class) {
      return false;
    }
    return ((ChessPosition) obj).getRow() == getRow() && ((ChessPosition) obj).getColumn() == getColumn();
  }

  @Override
  public int hashCode() {
    return Objects.hash(column, row);
  }
}
