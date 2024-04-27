package edu.austral.dissis.chess.piece;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.movement.PieceMovement;
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

  public Piece(List<PieceMovement> movements, Color pieceColour, PieceType type, boolean hasMoved, String id) {
    this.movements = movements;
    this.pieceColour = pieceColour;
    this.type = type;
    this.hasMoved = hasMoved;
    this.id = id;
  }

  public boolean isValidMove(ChessPosition oldPos, ChessPosition newPos, Board context) {
    for (PieceMovement movement : movements) {
      if (movement.isValidMove(oldPos, newPos, context)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public String toString() {
    String colour = pieceColour == Color.BLACK ? "BLACK" : "WHITE";
    char typeName = type != PieceType.KNIGHT ? type.toString().charAt(0) : type.toString().charAt(1);
    return colour.substring(0, 2) + " " + typeName;
  }

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

  public Piece changeMoveState() {
    return new Piece(movements, pieceColour, type, !hasMoved,id); // Immutable approach
  }

  public boolean hasNotMoved() {
    return !hasMoved;
  }

  public List<ChessPosition> getMoveSet(ChessPosition oldPos, Board context) {
    List<ChessPosition> moveList = new ArrayList<>();
    for (PieceMovement movement : movements) {
      moveList.addAll(movement.getPossibleMoves(oldPos, context));
    }
    moveList = new ArrayList<>(new HashSet<>(moveList));
    return moveList;
  }
}
