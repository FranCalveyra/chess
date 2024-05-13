package edu.austral.dissis.common.rules.premovement.rules;

import edu.austral.dissis.common.engine.BoardGame;
import edu.austral.dissis.common.piece.Piece;
import edu.austral.dissis.common.utils.move.GameMove;
import java.util.List;

public class PieceValidMove implements PreMovementRule {
  @Override
  public boolean isValidRule(GameMove move, BoardGame game) {
    Piece pieceToMove = game.getBoard().pieceAt(move.from());
    final List<GameMove> playToExecute = pieceToMove.getPlay(move, game.getBoard());
    // No moves available
    return !playToExecute.isEmpty();
  }
}
