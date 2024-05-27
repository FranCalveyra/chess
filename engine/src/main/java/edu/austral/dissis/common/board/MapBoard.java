package edu.austral.dissis.common.board;

import edu.austral.dissis.common.piece.Piece;
import edu.austral.dissis.common.utils.move.BoardPosition;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.jetbrains.annotations.NotNull;

public class MapBoard implements Board {

  // Simplest possible board implementation
  private final Map<BoardPosition, Piece> pieces;
  private final int rows;
  private final int columns;

  public MapBoard(Map<BoardPosition, Piece> pieces, int rows, int columns) {
    this.pieces = pieces;
    this.rows = rows;
    this.columns = columns;
  }

  // Default constructor for Chess Board
  public MapBoard(Map<BoardPosition, Piece> pieces) {
    this.pieces = pieces;
    this.rows = this.columns = 8;
  }

  // Board modifiers
  @Override
  public MapBoard addPieceAt(BoardPosition pos, Piece piece) {
    Map<BoardPosition, Piece> newMap = copyMap(pieces);
    newMap.put(pos, piece);
    return new MapBoard(newMap, rows, columns);
  }

  @Override
  public MapBoard removePieceAt(BoardPosition pos) {
    Map<BoardPosition, Piece> newMap = copyMap(pieces);
    newMap.remove(pos);
    return new MapBoard(newMap, rows, columns);
  }

  // Getters and extra stuff
  @Override
  public Piece pieceAt(BoardPosition pos) {
    return pieces.get(pos);
  }

  @Override
  public int getColumns() {
    return columns;
  }

  @Override
  public int getRows() {
    return rows;
  }

  @Override
  public Map<BoardPosition, Piece> getPiecesAndPositions() {
    return pieces;
  }

  @Override
  public String toString() {
    Piece[][] matrix = new Piece[rows][columns];
    for (Map.Entry<BoardPosition, Piece> entry : pieces.entrySet()) {
      matrix[entry.getKey().getRow()][entry.getKey().getColumn()] = entry.getValue();
    }
    return IntStream.iterate(matrix.length - 1, i -> i >= 0, i -> i - 1)
        .mapToObj(i -> Arrays.toString(matrix[i]) + "\n")
        .collect(Collectors.joining());
  }

  // Private stuff
  private @NotNull HashMap<BoardPosition, Piece> copyMap(Map<BoardPosition, Piece> pieces) {
    return new HashMap<>(pieces);
  }
}
