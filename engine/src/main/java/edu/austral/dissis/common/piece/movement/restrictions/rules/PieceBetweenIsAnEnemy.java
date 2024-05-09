package edu.austral.dissis.common.piece.movement.restrictions.rules;

import edu.austral.dissis.chess.utils.Pair;
import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.utils.move.BoardPosition;
import edu.austral.dissis.common.utils.move.GameMove;

public class PieceBetweenIsAnEnemy implements MovementRestriction {
  @Override
  public boolean isValidRestriction(GameMove move, Board context) {
    BoardPosition between = getPosBetween(move);
    return context.pieceAt(between).getPieceColour()
        != context.pieceAt(move.from()).getPieceColour();
  }

  private BoardPosition getPosBetween(GameMove move) {
    int rowDelta = move.to().getRow() - move.from().getRow();
    int colDelta = move.to().getColumn() - move.from().getColumn();
    return lastPos(move.from(), colDelta, rowDelta);
  }

  private BoardPosition lastPos(BoardPosition from, int deltaX, int deltaY) {
    if (deltaX > 0) {
      if (deltaY > 0) {
        return getEnemyPos(from, new Pair<>(1, 1));
      } else {
        return getEnemyPos(from, new Pair<>(-1, 1));
      }
    } else {
      if (deltaY > 0) {
        return getEnemyPos(from, new Pair<>(1, -1));
      } else {
        return getEnemyPos(from, new Pair<>(-1, -1));
      }
    }
  }

  private BoardPosition getEnemyPos(BoardPosition from, Pair<Integer, Integer> vector) {
    return new BoardPosition(from.getRow() + vector.first(), from.getColumn() + vector.second());
  }
}
