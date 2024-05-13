package edu.austral.dissis.common.rules.premovement.rules;

import edu.austral.dissis.chess.engine.BoardGame;
import edu.austral.dissis.common.piece.Piece;
import edu.austral.dissis.common.utils.move.GameMove;

public class TurnRule implements PreMovementRule {
  @Override
  public boolean isValidRule(GameMove move, BoardGame game) {
    Piece toMove = game.getBoard().pieceAt(move.from());
    return toMove.getPieceColour() == game.getCurrentTurn();
  }
}
