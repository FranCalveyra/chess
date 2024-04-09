package edu.austral.dissis.chess.engine;

import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.piece.PieceType;
import edu.austral.dissis.chess.promoter.Promoter;
import edu.austral.dissis.chess.turn.TurnSelector;
import edu.austral.dissis.chess.utils.Position;
import edu.austral.dissis.chess.utils.UnallowedMoveException;
import java.awt.Color;
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
  private final Color currentTurn;
  private final Piece[][] board;
  private final TurnSelector selector;
  private final int turnNumber;
  private final List<Piece> takenPieces;
  private final Promoter promoter;

  public Board(
      Map<Position, Piece> pieces,
      TurnSelector selector,
      int rows,
      int columns,
      List<Piece> takenPieces,
      Color currentTurn,
      Promoter promoter) {
    this.pieces = pieces;
    this.rows = rows;
    this.columns = columns;
    this.takenPieces = takenPieces;
    this.promoter = promoter;
    this.board = setup();
    this.selector = selector;
    this.currentTurn = currentTurn;
    this.turnNumber = 0;
  }

  public Board(Map<Position, Piece> pieces, TurnSelector selector, Promoter promoter) {
    this.pieces = pieces;
    this.selector = selector;
    this.promoter = promoter;
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
      Color currentTurn,
      Promoter promoter) {
    this.pieces = pieces;
    this.takenPieces = takenPieces;
    this.promoter = promoter;
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
      List<Piece> takenPieces,
      Promoter promoter) {
    this.pieces = pieces;
    this.promoter = promoter;
    this.rows = this.columns = 8;
    this.board = board;
    this.selector = selector;
    this.currentTurn = currentTurn;
    this.turnNumber = turnNumber;
    this.takenPieces = takenPieces;
  }

  public Board updatePiecePosition(Position oldPos, Position newPos, PieceType typeForPromotion)
      throws UnallowedMoveException {
    // First, ChessGame checks if the wanted piece to move is from its team
    // Do all needed checks
    if (checkOutOfBounds(newPos) || checkOutOfBounds(oldPos)) {
      return this; // Check out of bounds
    }
    Piece piece = pieceAt(oldPos); // Fetches piece position before moving
    if (piece == null) {
      return this;
    }
    if (!piece.checkValidMove(oldPos, newPos, this)) {
      throwException(oldPos, newPos); // Check move validity
    }

    // Now, move the piece. Take piece in newPos whether exists
    Board newBoard = new Board(pieces, selector, promoter);
    Piece pieceToTake = pieces.get(newPos);
    Color nextTurn;
    if (pieceToTake != null) {
      if (pieceToTake.getPieceColour() == piece.getPieceColour()) {
        return this;
      } else {
        newBoard = removePieceAt(oldPos).removePieceAt(newPos).addPieceAt(newPos, piece);
        if (promoter.hasToPromote(newBoard, piece.getPieceColour())
            || promoter.canPromote(newPos, newBoard)) {
          newBoard = promoter.promote(newPos, typeForPromotion, newBoard);
        }
        return new Board(
            newBoard.getPiecesAndPositions(),
            newBoard.getSelector(),
            changeTurn(selector.selectTurn(this, turnNumber + 1)),
            turnNumber + 1,
            newBoard.getBoard(),
            newBoard.getTakenPieces(),
            promoter);
      }
    } else {
      nextTurn = selector.selectTurn(this, turnNumber + 1);
      Map<Position, Piece> newPieces = copyMap(pieces);
      if (piece.hasNotMoved()) {
        newBoard = removePieceAt(oldPos).addPieceAt(newPos, piece);
        newPieces = newBoard.getPiecesAndPositions();
      }
      return new Board(
          newPieces,
          newBoard.getSelector(),
          nextTurn,
          newBoard.getTurnNumber() + 1,
          newBoard.getBoard(),
          newBoard.getTakenPieces(),
          promoter);
    }
  }

  // Board modifiers
  public Board addPieceAt(Position pos, Piece piece) {
    Piece[][] newBoard = board.clone();
    Piece changedPiece = piece.hasNotMoved() ? piece.changeMoveState() : piece;
    Map<Position, Piece> newMap = copyMap(pieces);
    newBoard[pos.getRow()][pos.getColumn()] = changedPiece; // Add it to board
    newMap.put(pos, changedPiece); // Get the map

    return new Board(newMap, selector, currentTurn, turnNumber, newBoard, takenPieces, promoter);
  }

  public Board removePieceAt(Position pos) {
    Piece[][] newBoard = board.clone();
    newBoard[pos.getRow()][pos.getColumn()] = null;
    Map<Position, Piece> newMap = copyMap(pieces);
    newMap.remove(pos);
    return new Board(newMap, selector, currentTurn, turnNumber, newBoard, takenPieces, promoter);
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

  public Promoter getPromoter() {
    return promoter;
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

  private void throwException(Position oldPos, Position newPos) throws UnallowedMoveException {
    throw new UnallowedMoveException(
        "Cannot move this piece from position ("
            + oldPos.getRow()
            + ", "
            + oldPos.getColumn()
            + ")"
            + "to position ("
            + newPos.getRow()
            + ", "
            + newPos.getColumn()
            + "). \n Its possible moves are: "
            + pieceAt(oldPos).getMoveSet(oldPos, this)
            + ". Type = "
            + pieceAt(oldPos).getType());
  }

  private boolean checkOutOfBounds(Position newPos) {
    return newPos.getRow() >= rows
        || newPos.getColumn() >= columns
        || newPos.getRow() < 0
        || newPos.getColumn() < 0;
  }
}
