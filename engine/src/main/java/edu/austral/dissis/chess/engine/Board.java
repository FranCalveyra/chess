package edu.austral.dissis.chess.engine;

import static edu.austral.dissis.chess.piece.PieceType.KING;
import static java.awt.Color.BLACK;
import static java.awt.Color.WHITE;

import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.utils.ChessPosition;
import java.awt.Color;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.jetbrains.annotations.NotNull;

public class Board {
  // Simplest possible board implementation
  private final Map<ChessPosition, Piece> pieces;
  private final int rows;
  private final int columns;
  private final ChessPosition whiteKingPosition;
  private final ChessPosition blackKingPosition;

  public Board(Map<ChessPosition, Piece> pieces, int rows, int columns) {
    this.pieces = pieces;
    this.rows = rows;
    this.columns = columns;
    whiteKingPosition = fetchKingPosition(pieces, WHITE);
    blackKingPosition = fetchKingPosition(pieces, BLACK);
  }

  // Default constructor for Chess Board
  public Board(Map<ChessPosition, Piece> pieces) {
    this.pieces = pieces;
    this.rows = this.columns = 8;
    whiteKingPosition = fetchKingPosition(pieces, WHITE);
    blackKingPosition = fetchKingPosition(pieces, BLACK);
  }

  public Board updatePiecePosition(ChessPosition oldPos, ChessPosition newPos) {
    Piece piece = pieceAt(oldPos);
    return removePieceAt(oldPos).addPieceAt(newPos, piece);
  }

  // Board modifiers
  public Board addPieceAt(ChessPosition pos, Piece piece) {
    Map<ChessPosition, Piece> newMap = copyMap(pieces);
    newMap.put(pos, piece);
    return new Board(newMap, rows, columns);
  }

  public Board removePieceAt(ChessPosition pos) {
    Map<ChessPosition, Piece> newMap = copyMap(pieces);
    if (!newMap.containsKey(pos)) {
      return this;
    }
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

  public ChessPosition getKingPosition(Color team) {
    return team == WHITE ? whiteKingPosition : blackKingPosition;
  }

  @Override
  public String toString() {
    Piece[][] matrix = new Piece[rows][columns];
    for (Map.Entry<ChessPosition, Piece> entry : pieces.entrySet()) {
      matrix[entry.getKey().getRow()][entry.getKey().getColumn()] = entry.getValue();
    }
    return IntStream.iterate(matrix.length - 1, i -> i >= 0, i -> i - 1)
        .mapToObj(i -> Arrays.toString(matrix[i]) + "\n")
        .collect(Collectors.joining());
  }

  public Board createCopy() {
    return new Board(pieces, rows, columns);
  }

  // Private stuff
  private @NotNull HashMap<ChessPosition, Piece> copyMap(Map<ChessPosition, Piece> pieces) {
    return new HashMap<>(pieces);
  }

  private ChessPosition fetchKingPosition(Map<ChessPosition, Piece> pieces, Color team) {
    for (Map.Entry<ChessPosition, Piece> entry : pieces.entrySet()) {
      Piece piece = entry.getValue();
      if (piece != null && piece.getPieceColour() == team && piece.getType() == KING) {
        return entry.getKey();
      }
    }
    return null;
  }
}
