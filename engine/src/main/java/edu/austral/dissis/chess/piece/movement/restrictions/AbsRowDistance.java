package edu.austral.dissis.chess.piece.movement.restrictions;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.utils.move.ChessMove;
import edu.austral.dissis.chess.utils.move.ChessPosition;

public class AbsRowDistance implements MovementRestriction {
  private final int rowDistance;

  public AbsRowDistance(int rowDistance) {
    this.rowDistance = rowDistance;
  }

  @Override
  public boolean isValidRestriction(ChessMove move, Board context) {
    ChessPosition oldPos = move.from();
    ChessPosition newPos = move.to();
    return Math.abs(newPos.getRow() - oldPos.getRow()) == rowDistance;
  }
}
