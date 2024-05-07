package edu.austral.dissis.common.utils.move;

import java.util.Objects;

public class BoardPosition {
  /** X,Y coordinates encapsulation. Try to do it one-based */
  private final int row;

  private final int column;

  public BoardPosition(int row, int column) {
    this.row = row;
    this.column = column;
  }

  public static BoardPosition fromAlgebraic(String pos) {
    int col = pos.charAt(0) - 'a';
    int row = pos.charAt(1) - '1';
    return new BoardPosition(row, col);
  }

  public static String toAlgebraic(BoardPosition pos) {
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
    if (obj == null || obj.getClass() != BoardPosition.class) {
      return false;
    }
    return ((BoardPosition) obj).getRow() == getRow()
        && ((BoardPosition) obj).getColumn() == getColumn();
  }

  @Override
  public int hashCode() {
    return Objects.hash(column, row);
  }
}
