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

  public static ChessPosition fromAlgebraic(String pos) {
    int col = pos.charAt(0) - 'a';
    int row = pos.charAt(1) - '1';
    return new ChessPosition(row, col);
  }

  public static String toAlgebraic(ChessPosition pos) {
    char col = (char) (pos.column + 'a');
    char row = (char) (pos.row + '1');
    return col + String.valueOf(row);
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
    return toAlgebraic(this);
  }

  // Easier map getting
  @Override
  public boolean equals(Object obj) {
    if (obj == null || obj.getClass() != ChessPosition.class) {
      return false;
    }
    return ((ChessPosition) obj).getRow() == getRow()
        && ((ChessPosition) obj).getColumn() == getColumn();
  }

  @Override
  public int hashCode() {
    return Objects.hash(column, row);
  }
}
