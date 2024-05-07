package edu.austral.dissis.common.rules.winconds;

import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.piece.Piece;
import edu.austral.dissis.common.utils.move.BoardPosition;
import java.awt.Color;
import java.util.Map;
import java.util.stream.Collectors;

public class Extinction implements WinCondition {
  private final Color team;

  public Extinction(Color team) {
    this.team = team;
  }

  @Override
  public boolean isValidRule(Board context) {
    // True whenever only the king is left on one of both sides is left
    Map<BoardPosition, Piece> teamPieces = getTeamPieces(context);
    return teamPieces.size() == 1;
  }

  @Override
  public Color getTeam() {
    return team;
  }

  private Map<BoardPosition, Piece> getTeamPieces(Board context) {
    Map<BoardPosition, Piece> map = context.getPiecesAndPositions();
    return map.entrySet().stream()
        .filter(entry -> entry.getValue() != null && entry.getValue().getPieceColour() == team)
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }
}
