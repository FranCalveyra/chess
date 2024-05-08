package edu.austral.dissis.common.rules.premovement;

import edu.austral.dissis.chess.engine.ChessGame;
import edu.austral.dissis.common.piece.Piece;
import edu.austral.dissis.common.utils.move.GameMove;

public class TurnRule implements PreMovementRule {
  @Override
  public boolean isValidRule(GameMove move, ChessGame game) {
    Piece toMove = game.getBoard().pieceAt(move.from());
    return toMove.getPieceColour() == game.getCurrentTurn();
  }
}
