package edu.austral.dissis.chess.rules.premovement;

import edu.austral.dissis.chess.engine.ChessGame;
import edu.austral.dissis.chess.utils.move.ChessMove;

public class PieceAtPosition implements PreMovementRule {
  @Override
  public boolean isValidRule(ChessMove move, ChessGame game) {
    return game.getBoard().pieceAt(move.from()) != null;
  }

  @Override
  public String getStringErrorRepresentation() {
    return "No piece at that position";
  }
}
