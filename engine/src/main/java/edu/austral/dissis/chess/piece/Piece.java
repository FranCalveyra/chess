package edu.austral.dissis.chess.piece;

import edu.austral.dissis.chess.rule.movement.PieceMovementRule;
import edu.austral.dissis.chess.utils.Position;
import java.awt.Color;
import java.util.List;

public class Piece {
  private final List<PieceMovementRule> movementRules;
  private final Color pieceColour;
  private final PieceType type;
  private boolean isActiveInBoard = true;

  public Piece(List<PieceMovementRule> movementRules, Color pieceColour, PieceType type) {
    this.movementRules = movementRules;
    this.pieceColour = pieceColour;
    this.type = type;
  }

  public boolean checkValidMove(Position oldPos, Position newPos) {
    for (PieceMovementRule rule : movementRules) {
      if (!rule.isValidMove(oldPos, newPos, )) {
        return false;
      }
    }
    return true;
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
}
