package edu.austral.dissis.chess.engine;

import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.turn.TurnSelector;
import edu.austral.dissis.chess.utils.Position;
import edu.austral.dissis.chess.utils.UnallowedMoveException;
import java.awt.Color;
import java.util.*;

public class Board {
  private final Map<Position, Piece> pieces;
  private final int rows;
  private final int columns;
  private Color currentTurn;
  private final Piece[][] board;
  private final TurnSelector selector;
  private int turnNumber = 0;
  private final List<Piece> takenPieces = new ArrayList<>(); //TODO

  public Board(Map<Position, Piece> pieces, int rows, int columns, TurnSelector selector) {
    this.pieces = pieces;
    this.rows = rows;
    this.columns = columns;
    this.board = new Piece[rows][columns];
    this.selector = selector;
  }

  // Default constructor for Chess Board
  public Board(Map<Position, Piece> pieces, TurnSelector selector) {
    this.pieces = pieces;
    this.rows = this.columns = 8;
    this.board = new Piece[rows][columns];
    this.selector = selector;
    setup();
  }

  public Map<Position, Piece> getActivePiecesAndPositions() {
    return pieces;
  }

  public void updatePiecePosition(Position newPos, Piece piece) throws UnallowedMoveException {
    // First, ChessGame checks if the wanted piece to move is from its team
    int i = newPos.getRow();
    int j = newPos.getColumn();
    // Do all needed checks
    if (i > rows || j > columns || i < 0 || j < 0) {
      return; // Check out of bounds
    }
    if (!piece.isActiveInBoard()) {
      return; // Check piece activity
    }
    Position oldPos = getPieceCurrentPosition(piece); // Fetches piece position before moving
    if (!piece.checkValidMove(oldPos, newPos, this)) {
      throw new UnallowedMoveException(
          "Cannot move this piece to that position"); // Check move validity
    }
    // Now, move the piece. Take piece in newPos whether exists
    Piece pieceToTake = board[i][j];
    if (pieceToTake != null) {
      if (pieceToTake.getPieceColour() == piece.getPieceColour()) {
        return;
      }
      removePieceAt(newPos);
      removePieceAt(oldPos);
      takenPieces.add(pieceToTake);
      addPieceAt(newPos, piece);
    }
    updateToEmptyPosition(piece, oldPos, i, j);
    if (!piece.hasMoved()) {
      piece.changeMoveState();
    }
    turnNumber++;
    changeTurn(selector.selectTurn(this,turnNumber));

    // Debugging code ahead:
    printBoard();
    // This should work, check later
  }

  public void addPieceAt(Position pos, Piece piece) {
    piece.changePieceActivity(); // Activate piece
    board[pos.getRow()][pos.getColumn()] = piece; // Add it to board
    pieces.put(pos, piece); // Get the map
  }

  public void removePieceAt(Position pos) {
    board[pos.getRow()][pos.getColumn()] = null;
    Piece pieceToRemove = pieces.get(pos);
    pieceToRemove.changePieceActivity();
    pieces.remove(pos);
  }

  public Piece pieceAt(Position pos) {
    return pieces.get(pos);
  }

  public Color getCurrentTurn() {
    return currentTurn;
  }

  public void changeTurn(Color turn) {
    currentTurn =turn;
  } // Uncouple turn

  public int getColumns() {
    return columns;
  }
  public int getRows() {
    return rows;
  }

  // Private methods
  private Position getPieceCurrentPosition(Piece piece) {
    // O(N)
    for (Map.Entry<Position, Piece> entry : pieces.entrySet()) {
      if (entry.getValue() == piece) {
        return entry.getKey();
      }
    }
    throw new NoSuchElementException("Piece does not exist");
  }

  private void setup() {
    for (Map.Entry<Position, Piece> entry : pieces.entrySet()) {
      int i = entry.getKey().getRow();
      int j = entry.getKey().getColumn();
      board[i][j] = entry.getValue();
    }
  }

  private void updateToEmptyPosition(Piece piece, Position oldPos, int i, int j) {
    board[oldPos.getRow()][oldPos.getColumn()] = null;
    pieces.remove(oldPos);
    board[i][j] = piece;
    pieces.put(new Position(i, j), piece);
  }

  private void printBoard() {
      for (Piece[] value : board) {
          System.out.println(Arrays.toString(value));
      }
    System.out.println("\n");
    System.out.println(pieces.size());
    System.out.println("\n");
  }
}
