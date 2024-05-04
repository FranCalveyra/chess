package edu.austral.dissis.chess.piece.movement.restrictions;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.utils.ChessMove;

public class ClearTile implements MovementRestriction {
  @Override
  public boolean isValidRestriction(ChessMove move, Board context) {
    return context.pieceAt(move.to()) == null;
  }
}
