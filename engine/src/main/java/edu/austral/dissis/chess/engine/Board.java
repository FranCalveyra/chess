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
  private final Color currentTurn;
  private final Piece[][] board;
  private final TurnSelector selector;
  private final int turnNumber;
  private final List<Piece> takenPieces;

  public Board(
      Map<Position, Piece> pieces,
      TurnSelector selector,
      int rows,
      int columns,
      List<Piece> takenPieces,
      Color currentTurn) {
    this.pieces = pieces;
    this.rows = rows;
    this.columns = columns;
    this.takenPieces = takenPieces;
    this.board = setup();
    this.selector = selector;
    this.currentTurn = currentTurn;
    this.turnNumber = 0;
  }

  public Board(Map<Position, Piece> pieces, TurnSelector selector) {
    this.pieces = pieces;
    this.selector = selector;
    this.rows = this.columns = 8;
    this.currentTurn = changeTurn(Color.WHITE);
    this.takenPieces = new ArrayList<>();
    this.turnNumber = 0;
    this.board = setup();
  }

  // Default constructor for Chess Board
  public Board(
      Map<Position, Piece> pieces,
      TurnSelector selector,
      List<Piece> takenPieces,
      Color currentTurn) {
    this.pieces = pieces;
    this.takenPieces = takenPieces;
    this.rows = this.columns = 8;
    this.board = setup();
    this.selector = selector;
    this.currentTurn = currentTurn;
    this.turnNumber = 0;
  }

  // Default private immutable constructor
  private Board(
      Map<Position, Piece> pieces,
      TurnSelector selector,
      Color currentTurn,
      int turnNumber,
      Piece[][] board,
      List<Piece> takenPieces) {
    this.pieces = pieces;
    this.rows = this.columns = 8;
    this.board = board;
    this.selector = selector;
    this.currentTurn = currentTurn;
    this.turnNumber = turnNumber;
    this.takenPieces = takenPieces;
  }

  public Board updatePiecePosition(Position newPos, Piece piece) throws UnallowedMoveException {
    // First, ChessGame checks if the wanted piece to move is from its team
    int i = newPos.getRow();
    int j = newPos.getColumn();
    // Do all needed checks
    if (i > rows || j > columns || i < 0 || j < 0) {
      return this; // Check out of bounds
    }
    if (!piece.isActiveInBoard()) {
      return this; // Check piece activity
    }
    Position oldPos = getPieceCurrentPosition(piece); // Fetches piece position before moving
    if (!piece.checkValidMove(oldPos, newPos, this)) {
      throw new UnallowedMoveException(
          "Cannot move this piece to that position"); // Check move validity
    }
    Board newBoard;
    // Now, move the piece. Take piece in newPos whether exists
    Piece pieceToTake = board[i][j];
    if (pieceToTake != null) {
      if (pieceToTake.getPieceColour() == piece.getPieceColour()) {
        return this;
      }
      Board otherBoard = removePieceAt(newPos).removePieceAt(oldPos);
      List<Piece> newTakenPieces = new ArrayList<>(List.copyOf(takenPieces));
      newTakenPieces.add(pieceToTake);
      newBoard = otherBoard.addPieceAt(newPos, piece);
      return new Board(
          newBoard.getActivePiecesAndPositions(),
          newBoard.getSelector(),
          newBoard.changeTurn(getSelector().selectTurn(newBoard, newBoard.getTurnNumber() + 1)),
          newBoard.getTurnNumber() + 1,
          newBoard.getBoard(),
          newTakenPieces);
    }
    newBoard = updateToEmptyPosition(piece, oldPos, i, j);
    if (piece.hasNotMoved()) {
      piece.changeMoveState();
    }
    Color newTurn = changeTurn(selector.selectTurn(this, turnNumber + 1));
    return new Board(
        newBoard.getActivePiecesAndPositions(),
        newBoard.getSelector(),
        newTurn,
        newBoard.getTurnNumber() + 1,
        newBoard.getBoard(),
        newBoard.takenPieces);
    // This should work, check later
  }

  public Board addPieceAt(Position pos, Piece piece) {
    Piece[][] newBoard = board.clone();
    newBoard[pos.getRow()][pos.getColumn()] = piece; // Add it to board
    Map<Position, Piece> newMap = new HashMap<>(Map.copyOf(pieces));
    newMap.put(pos, piece); // Get the map

    return new Board(newMap, selector, currentTurn, turnNumber, newBoard, takenPieces);
  }

  public Board removePieceAt(Position pos) {
    Piece[][] newBoard = board.clone();
    newBoard[pos.getRow()][pos.getColumn()] = null;
    Piece pieceToRemove = pieces.get(pos);
    Map<Position, Piece> newMap = new HashMap<>(Map.copyOf(pieces));
    pieceToRemove.changePieceActivity();
    newMap.remove(pos);
    return new Board(newMap, selector, currentTurn, turnNumber, newBoard, takenPieces);
  }

  // Getters and extra stuff

  public Piece pieceAt(Position pos) {
    return pieces.get(pos);
  }

  public Color getCurrentTurn() {
    return currentTurn;
  }

  public Color changeTurn(Color turn) {
    return turn;
  } // Uncouple turn

  public int getColumns() {
    return columns;
  }

  public int getRows() {
    return rows;
  }

  public Map<Position, Piece> getActivePiecesAndPositions() {
    return pieces;
  }

  public TurnSelector getSelector() {
    return selector;
  }

  public List<Piece> getTakenPieces() {
    return takenPieces;
  }

  public Piece[][] getBoard() {
    return board;
  }

  public int getTurnNumber() {
    return turnNumber;
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

  private Piece[][] setup() {
    Piece[][] newBoard = new Piece[rows][columns];
    for (Map.Entry<Position, Piece> entry : pieces.entrySet()) {
      int i = entry.getKey().getRow();
      int j = entry.getKey().getColumn();
      newBoard[i][j] = entry.getValue();
    }
    return newBoard;
  }

  private Board updateToEmptyPosition(Piece piece, Position oldPos, int i, int j) {
    Piece[][] newBoard = Arrays.copyOf(board, board.length);
    newBoard[oldPos.getRow()][oldPos.getColumn()] = null;
    Map<Position, Piece> newMap = new HashMap<>(Map.copyOf(pieces));
    newMap.remove(oldPos);
    newBoard[i][j] = piece;
    newMap.put(new Position(i, j), piece);
    return new Board(newMap, selector, currentTurn, turnNumber, newBoard, takenPieces);
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    for (int i = board.length - 1; i >= 0; i--) {
      String line = Arrays.toString(board[i]);
      builder.append(line).append("\n");
    }
    return builder.toString();
  }
}
