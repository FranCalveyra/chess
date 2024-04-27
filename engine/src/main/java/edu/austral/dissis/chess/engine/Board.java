package edu.austral.dissis.chess.engine;

import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.utils.ChessPosition;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public class Board {
  private final Map<ChessPosition, Piece> pieces;
  private final int rows;
  private final int columns;

  public Board(Map<ChessPosition, Piece> pieces, int rows, int columns) {
    this.pieces = pieces;
    this.rows = rows;
    this.columns = columns;
  }

  // Default constructor for Chess Board
  public Board(Map<ChessPosition, Piece> pieces) {
    this.pieces = pieces;
    this.rows = this.columns = 8;
  }

  public Board updatePiecePosition(ChessPosition oldPos, ChessPosition newPos) {
    Piece piece = pieceAt(oldPos);
    return removePieceAt(oldPos).addPieceAt(newPos, piece);
  }

  // Board modifiers
  public Board addPieceAt(ChessPosition pos, Piece piece) {
    Map<ChessPosition, Piece> newMap = copyMap(pieces);
    // Add it to board
    newMap.put(pos, piece); // Update the map

    return new Board(newMap, rows, columns);
  }

  public Board removePieceAt(ChessPosition pos) {
    Map<ChessPosition, Piece> newMap = copyMap(pieces);
    newMap.remove(pos);
    return new Board(newMap, rows, columns);
  }

  // Getters and extra stuff

  public Piece pieceAt(ChessPosition pos) {
    return pieces.get(pos);
  }

  public int getColumns() {
    return columns;
  }

  public int getRows() {
    return rows;
  }

  public Map<ChessPosition, Piece> getPiecesAndPositions() {
    return pieces;
  }

  @Override
  public String toString() {
    Piece[][] matrix = new Piece[rows][columns];
    for (Map.Entry<ChessPosition, Piece> entry : pieces.entrySet()) {
      matrix[entry.getKey().getRow()][entry.getKey().getColumn()] = entry.getValue();
    }
    StringBuilder builder = new StringBuilder();
    for (Piece[] value : matrix) {
      builder.append(Arrays.toString(value)).append("\n");
    }
    return builder.toString();
  }

  public Board createCopy() {
    return new Board(pieces, rows, columns);
  }

  // Private stuff
  private @NotNull HashMap<ChessPosition, Piece> copyMap(Map<ChessPosition, Piece> pieces) {
    return new HashMap<>(pieces);
  }
}
