package edu.austral.dissis.chess.utils;

public class Position {
  private final int column, row;
  public Position(int row, int column){
    this.row = row;
    this.column = column;
  }

  public int getColumn() {
    return column;
  }

  public int getRow() {
    return row;
  }
}
