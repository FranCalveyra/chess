package edu.austral.dissis.chess.engine;

import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.utils.Position;
import java.awt.Color;
import java.util.Map;
import java.util.NoSuchElementException;

public class Board {
  private final Map<Position, Piece> pieces;
  private final int rows;
  private final int columns;
  private Color currentTurn;
  private final Piece[][] board;

  public Board(Map<Position, Piece> pieces, int rows, int columns) {
    this.pieces = pieces;
    this.rows = rows;
    this.columns = columns;
    this.board = new Piece[rows][columns];
  }

  // Default constructor for Chess Board
  public Board(Map<Position, Piece> pieces) {
    this.pieces = pieces;
    this.rows = this.columns = 8;
    this.board = new Piece[8][8];
    setup();
  }

  public Map<Position, Piece> getActivePiecesAndPositions() {
    return pieces;
  }

  public void updatePiecePosition(Position newPos, Piece piece) {
    // First, ChessGame checks if the wanted piece to move is from its team
    int i = newPos.getRow();
    int j = newPos.getColumn();
    // Do all needed checks
    if (i > rows || j > columns || i<0 || j<0) {
      return; // Check out of bounds
    }
    if (!piece.isActiveInBoard()) {
      return; // Check piece activity
    }
    Position oldPos = getPieceCurrentPosition(piece); // Fetches piece position before moving
    if (!piece.checkValidMove(oldPos, newPos,this)) {
      return; // Check move validity
    }
    // Now, move the piece. Take piece in newPos whether exists
    Piece pieceToTake = board[i][j];
    if (pieceToTake != null) {
      if (pieceToTake.getPieceColour() != piece.getPieceColour()) {
        // Taking a piece
        pieces.replace(newPos, piece);
        pieces.remove(oldPos);
      }
      return;
    }
    board[i][j] = piece;
    pieces.remove(oldPos);
    pieces.put(new Position(i,j), piece);

    // This should work, check later
  }

  public Color getCurrentTurn() {
    return currentTurn;
  }

  public void changeTurn(Color turn) {
    currentTurn = turn;
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
}
