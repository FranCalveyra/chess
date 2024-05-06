package edu.austral.dissis.chess.piece;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.movement.PieceMovement;
import edu.austral.dissis.chess.utils.move.ChessMove;
import edu.austral.dissis.chess.utils.move.ChessPosition;
import java.awt.Color;
import java.util.List;
import java.util.stream.Collectors;

public class Piece {
  private final List<PieceMovement> movements;
  private final Color pieceColour;
  private final PieceType type;
  private final int moveCounter;
  private final String id;

  public Piece(List<PieceMovement> movements, Color pieceColour, PieceType type, String id) {
    this.movements = movements;
    this.pieceColour = pieceColour;
    this.type = type;
    this.id = id;
    this.moveCounter = 0;
  }

  public Piece(
      List<PieceMovement> movements,
      Color pieceColour,
      PieceType type,
      int moveCounter,
      String id) {
    this.movements = movements;
    this.pieceColour = pieceColour;
    this.type = type;
    this.moveCounter = moveCounter;
    this.id = id;
  }

  public boolean isValidMove(ChessMove move, Board context) {
    return movements.stream().anyMatch(movement -> movement.isValidMove(move, context));
  }

  @Override
  public String toString() {
    String colour = pieceColour == Color.BLACK ? "BLACK" : "WHITE";
    char typeName =
        type != PieceType.KNIGHT ? type.toString().charAt(0) : type.toString().charAt(1);
    return colour.substring(0, 2) + " " + typeName;
  }

  // Getters
  public Color getPieceColour() {
    return pieceColour;
  }

  public PieceType getType() {
    return type;
  }

  public List<PieceMovement> getMovements() {
    return movements;
  }

  public String getId() {
    return id;
  }

  public boolean hasNotMoved() {
    return moveCounter <= 0;
  }

  public int getMoveCounter() {
    return moveCounter;
  }

  // Own methods
  public Piece changeMoveState() {
    return new Piece(movements, pieceColour, type, moveCounter + 1, id);
  }

  public List<ChessPosition> getMoveSet(ChessPosition currentPos, Board context) {
    return movements.stream()
        .flatMap(movement -> movement.getPossiblePositions(currentPos, context).stream())
        .distinct()
        .collect(Collectors.toList());
  }

  public List<ChessMove> getPlay(ChessMove move, Board board) {
    return movements.stream()
        .filter(movement -> movement.isValidMove(move, board))
        .flatMap(movement -> movement.getMovesToExecute(move, board).stream())
        .collect(Collectors.toList());
  }
}
