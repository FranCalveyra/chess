package edu.austral.dissis.chess.rule.movement;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.utils.Position;

public class PawnTakingRule implements PieceMovementRule {

  @Override
  public boolean isValidMove(Position oldPos, Position newPos, Board context) {
    int oldX = oldPos.getColumn();
    int oldY = oldPos.getRow();
    int newX = newPos.getColumn();
    int newY = newPos.getRow();
    boolean diagonalMove = Math.abs(newY - oldY) ==1 && Math.abs(newX - oldX) == 1;
    if (!diagonalMove) {
      return false;
    }
    Piece pieceAtNewPos = context.getActivePiecesAndPositions().get(newPos);
    return pieceAtNewPos != null && pieceAtNewPos.getPieceColour() == context.getCurrentTurn();
  }
}
