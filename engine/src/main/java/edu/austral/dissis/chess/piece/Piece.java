package edu.austral.dissis.chess.piece;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.movement.PieceMovement;
import edu.austral.dissis.chess.utils.ChessMove;
import edu.austral.dissis.chess.utils.ChessPosition;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Piece {
  private final List<PieceMovement> movements;
  private final Color pieceColour;
  private final PieceType type;
  private final boolean hasMoved;
  private final String id;

  public Piece(List<PieceMovement> movements, Color pieceColour, PieceType type, String id) {
    this.movements = movements;
    this.pieceColour = pieceColour;
    this.type = type;
    this.id = id;
    this.hasMoved = false;
  }

  public Piece(
      List<PieceMovement> movements,
      Color pieceColour,
      PieceType type,
      boolean hasMoved,
      String id) {
    this.movements = movements;
    this.pieceColour = pieceColour;
    this.type = type;
    this.hasMoved = hasMoved;
    this.id = id;
  }

  public boolean isValidMove(ChessMove move, Board context) {
    return movements.stream().anyMatch(movement -> movement.isValidMove(move,context));
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

  public boolean hasMoved() {
    return hasMoved;
  } // TODO: Change to move counter

  // Own methods
  public Piece changeMoveState() {
    return new Piece(movements, pieceColour, type, !hasMoved, id);
  }

  public List<ChessPosition> getMoveSet(ChessPosition currentPos, Board context) {
    List<ChessPosition> positionList = new ArrayList<>();
    for (PieceMovement movement : movements) {
      positionList.addAll(movement.getPossiblePositions(currentPos, context));
    }
    positionList = new ArrayList<>(new HashSet<>(positionList)); // Remove repeated
    return positionList;
  }

  public List<ChessMove> getPlay(ChessMove move, Board board) {
    List<ChessMove> play = new ArrayList<>();
    for (PieceMovement movement : movements) {
      if (movement.isValidMove(move, board)) {
        play.addAll(movement.getMovesToExecute(move, board));
      }
    }
    return play;
  }
}
