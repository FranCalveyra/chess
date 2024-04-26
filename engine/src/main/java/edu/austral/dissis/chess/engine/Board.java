package edu.austral.dissis.chess.engine;

import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.utils.Position;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public class Board {
  private final Map<Position, Piece> pieces;
  private final int rows;
  private final int columns;

  public Board(Map<Position, Piece> pieces, int rows, int columns) {
    this.pieces = pieces;
    this.rows = rows;
    this.columns = columns;
  }

  // Default constructor for Chess Board
  public Board(Map<Position, Piece> pieces) {
    this.pieces = pieces;
    this.rows = this.columns = 8;
  }

  public Board updatePiecePosition(Position oldPos, Position newPos) {
    Piece piece = pieceAt(oldPos);
    return removePieceAt(oldPos).addPieceAt(newPos, piece);
  }



  // Board modifiers
  public Board addPieceAt(Position pos, Piece piece) {
    Map<Position, Piece> newMap = copyMap(pieces);
    // Add it to board
    newMap.put(pos, piece); // Update the map

    return new Board(newMap, rows, columns);
  }

  public Board removePieceAt(Position pos) {
    Map<Position, Piece> newMap = copyMap(pieces);
    newMap.remove(pos);
    return new Board(newMap, rows, columns);
  }

  // Getters and extra stuff

  public Piece pieceAt(Position pos) {
    return pieces.get(pos);
  }

  public int getColumns() {
    return columns;
  }

  public int getRows() {
    return rows;
  }

  public Map<Position, Piece> getPiecesAndPositions() {
    return pieces;
  }

  @Override
  public String toString() {
    Piece[][] matrix = new Piece[rows][columns];
    for (Map.Entry<Position, Piece> entry : pieces.entrySet()) {
      matrix[entry.getKey().getRow()][entry.getKey().getColumn()] = entry.getValue();
    }
    StringBuilder sb = new StringBuilder();
    for (int i = matrix.length-1; i >=0 ; i--) {
      sb.append(Arrays.toString(matrix[i])).append("\n");
    }
    return sb.toString();
  }

  public Board createCopy(){
    return new Board(pieces, rows, columns);
  }

  //Private stuff
  private @NotNull HashMap<Position, Piece> copyMap(Map<Position, Piece> pieces) {
    return new HashMap<>(pieces);
  }
}
