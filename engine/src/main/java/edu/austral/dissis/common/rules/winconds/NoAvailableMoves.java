package edu.austral.dissis.common.rules.winconds;

import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.piece.Piece;
import edu.austral.dissis.common.utils.move.BoardPosition;
import java.awt.Color;
import java.util.Map;
import java.util.stream.Collectors;

public class NoAvailableMoves implements WinCondition {
  private final Color team;

  public NoAvailableMoves(Color team) {
    this.team = team;
  }

  @Override
  public boolean isValidRule(Board context) {
    return teamHasNoAvailableMoves(context, team);
  }

  private boolean teamHasNoAvailableMoves(Board context, Color team) {
    Map<BoardPosition, Piece> teamPieces = getPiecesByTeam(context, team);
    if (teamPieces.isEmpty()) {
      return true;
    }

    for (Map.Entry<BoardPosition, Piece> pieceEntry : teamPieces.entrySet()) {
      if (!pieceEntry.getValue().getMoveSet(pieceEntry.getKey(), context).isEmpty()) {
        return false;
      }
    }
    return true;
  }

  private Map<BoardPosition, Piece> getPiecesByTeam(Board context, Color team) {
    return context.getPiecesAndPositions().entrySet().stream()
        .filter(entry -> entry.getValue().getPieceColour() == team)
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }

  @Override
  public Color getTeam() {
    return team;
  }
}
