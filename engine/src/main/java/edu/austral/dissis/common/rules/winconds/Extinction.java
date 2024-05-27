package edu.austral.dissis.common.rules.winconds;

import static edu.austral.dissis.common.utils.AuxStaticMethods.getPiecesByColor;

import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.piece.Piece;
import edu.austral.dissis.common.utils.move.BoardPosition;
import java.awt.Color;
import java.util.Map;

public class Extinction implements WinCondition {
  private final Color team;

  public Extinction(Color team) {
    this.team = team;
  }

  @Override
  public boolean isValidRule(Board context) {
    // True whenever only the king is left on one of both sides is left
    Map<BoardPosition, Piece> teamPieces = getTeamPieces(context);
    return teamPieces.isEmpty();
  }

  @Override
  public Color getTeam() {
    return team;
  }

  private Map<BoardPosition, Piece> getTeamPieces(Board context) {
    return getPiecesByColor(context, team);
  }
}
