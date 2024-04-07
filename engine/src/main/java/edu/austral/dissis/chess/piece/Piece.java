package edu.austral.dissis.chess.piece;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.movement.PieceMovement;
import edu.austral.dissis.chess.utils.Position;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Piece {
  private final List<PieceMovement> movements;
  private final Color pieceColour;
  private final PieceType type;
  private boolean isActiveInBoard = true;
  private boolean hasMoved = false;

  public Piece(List<PieceMovement> movements, Color pieceColour, PieceType type) {
    this.movements = movements;
    this.pieceColour = pieceColour;
    this.type = type;
  }

  public boolean checkValidMove(Position oldPos, Position newPos, Board context) {
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

    return colour.substring(0, 2) + " " + type.toString().charAt(0);
  }

  public boolean isActiveInBoard() {
    return isActiveInBoard;
  }

  public void changePieceActivity() {
    isActiveInBoard = !isActiveInBoard;
  }

  public Color getPieceColour() {
    return pieceColour;
  }

  public PieceType getType() {
    return type;
  }

  public void changeMoveState() {
    hasMoved = !hasMoved;
  }

  public boolean hasNotMoved() {
    return !hasMoved;
  }

  public List<Position> getMoveSet(Position oldPos, Board context) {
    List<Position> moveList = new ArrayList<>();
    for (PieceMovement movement : movements) {
      moveList.addAll(movement.getPossibleMoves(oldPos, context));
    }
    moveList = new ArrayList<>(new HashSet<>(moveList));
    return moveList;
  }
}
