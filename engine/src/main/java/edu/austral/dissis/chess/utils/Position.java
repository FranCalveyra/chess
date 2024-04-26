package edu.austral.dissis.chess.utils;

import java.util.Objects;

public class Position {
  /** X,Y coordinates encapsulation.
   * Try to do it one-based */
  private final int row;

  private final int column;

  public Position(int row, int column) {
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
    if (obj == null) {
      return false;
    }
    if (obj.getClass() != Position.class) {
      return false;
    }
    return ((Position) obj).getRow() == getRow() && ((Position) obj).getColumn() == getColumn();
  }

  @Override
  public int hashCode() {
    return Objects.hash(column, row);
  }
}
