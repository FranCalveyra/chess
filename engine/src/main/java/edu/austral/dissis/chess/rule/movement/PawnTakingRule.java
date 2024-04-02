package edu.austral.dissis.chess.rule.movement;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.utils.Position;

public class PawnTakingRule implements PieceMovementRule {
  private final Board context;

  public PawnTakingRule(Board context) {
    this.context = context;
  }

  @Override
  public boolean isValidMove(Position oldPos, Position newPos) {
    int oldX = oldPos.getColumn();
    int oldY = oldPos.getRow();
    int newX = newPos.getColumn();
    int newY = newPos.getRow();
    boolean diagonalPositiveMove = (newY - oldY) > 0 && (newX - oldX) > 0;
    if (!diagonalPositiveMove) {
      return false;
    }
    Piece pieceAtNewPos = context.getActivePiecesAndPositions().get(newPos);
    return pieceAtNewPos != null;
  }
}
