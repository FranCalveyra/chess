package edu.austral.dissis.chess.engine;

import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.utils.Position;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public class Board {
  private final Map<Position, Piece> pieces;
  private final int rows;
  private final int columns;
  private final Piece[][] matrix;
  private final List<Piece> takenPieces;

  public Board(Map<Position, Piece> pieces, int rows, int columns, List<Piece> takenPieces) {
    this.pieces = pieces;
    this.rows = rows;
    this.columns = columns;
    this.takenPieces = takenPieces;
    this.matrix = setup();
  }

  public Board(Map<Position, Piece> pieces) {
    this.pieces = pieces;
    this.rows = this.columns = 8;
    this.takenPieces = new ArrayList<>();
    this.matrix = setup();
  }

  // Default constructor for Chess Board
  public Board(Map<Position, Piece> pieces, List<Piece> takenPieces) {
    this.pieces = pieces;
    this.takenPieces = takenPieces;
    this.rows = this.columns = 8;
    this.matrix = setup();
  }

  // Default private immutable constructor
  private Board(
      Map<Position, Piece> pieces,
      int rows,
      int columns,
      Piece[][] matrix, // Deletable
      List<Piece> takenPieces) {
    this.pieces = pieces;
    this.rows = rows;
    this.columns = columns;
    this.matrix = matrix;
    this.takenPieces = takenPieces;
  }

  public Board updatePiecePosition(Position oldPos, Position newPos) {
    // First, ChessGame checks if the wanted piece to move is from its team
    // Do all needed checks
    Piece piece = pieceAt(oldPos);
    return removePieceAt(oldPos).addPieceAt(newPos, piece);
  }

  // Board modifiers
  public Board addPieceAt(Position pos, Piece piece) {
    Piece[][] newBoard = matrix.clone();
    Map<Position, Piece> newMap = copyMap(pieces);
    newBoard[pos.getRow()][pos.getColumn()] = piece; // Add it to board
    newMap.put(pos, piece); // Update the map

    return new Board(newMap, rows, columns, newBoard, takenPieces);
  }

  public Board removePieceAt(Position pos) {
    Piece[][] newBoard = matrix.clone();
    newBoard[pos.getRow()][pos.getColumn()] = null;
    Map<Position, Piece> newMap = copyMap(pieces);
    newMap.remove(pos);
    return new Board(newMap, rows, columns, newBoard, takenPieces);
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

  public List<Piece> getTakenPieces() {
    return takenPieces;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    for (int i = matrix.length - 1; i >= 0; i--) {
      String line = Arrays.toString(matrix[i]);
      builder.append(line).append("\n");
    }
    return builder.toString();
  }

  // Private stuff
  private Piece[][] setup() {
    Piece[][] newBoard = new Piece[rows][columns];
    for (Map.Entry<Position, Piece> entry : pieces.entrySet()) {
      int i = entry.getKey().getRow();
      int j = entry.getKey().getColumn();
      newBoard[i][j] = entry.getValue();
    }
    return newBoard;
  }

  private @NotNull HashMap<Position, Piece> copyMap(Map<Position, Piece> pieces) {
    return new HashMap<>(Map.copyOf(pieces));
  }
}
