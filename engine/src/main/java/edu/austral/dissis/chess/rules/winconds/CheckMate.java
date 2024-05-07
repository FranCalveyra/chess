package edu.austral.dissis.chess.rules.winconds;

import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.piece.Piece;
import edu.austral.dissis.common.rules.winconds.WinCondition;
import edu.austral.dissis.common.utils.move.BoardPosition;
import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class CheckMate implements WinCondition {
  private final Color team;

  public CheckMate(Color team) {
    this.team = team;
  }

  @Override
  public boolean isValidRule(Board context) {
    return checkmate(context, team);
  }

  @Override
  public Color getTeam() {
    return team;
  }

  private boolean checkmate(Board context, Color team) {
    if (!new DefaultCheck(team).isValidRule(context)) {
      return false;
    }
    Map<BoardPosition, Piece> teamPieces = getPiecesByColor(context, team);
    Map<BoardPosition, List<BoardPosition>> piecesWithPossibleMoves =
        getPieceMovesMap(teamPieces, context);
    return kingHasNoPossibleSaving(piecesWithPossibleMoves, context);
  }

  private Map<BoardPosition, List<BoardPosition>> getPieceMovesMap(
      Map<BoardPosition, Piece> teamPieces, Board context) {
    Map<BoardPosition, List<BoardPosition>> piecesWithPossibleMoves = new HashMap<>();
    teamPieces.forEach(
        (pos, piece) -> {
          List<BoardPosition> moveSet = piece.getMoveSet(pos, context);
          if (moveSet.isEmpty()) {
            return;
          }
          piecesWithPossibleMoves.put(pos, moveSet);
        });
    return piecesWithPossibleMoves;
  }

  private Map<BoardPosition, Piece> getPiecesByColor(Board context, Color team) {
    Map<BoardPosition, Piece> map = context.getPiecesAndPositions();
    return map.entrySet().stream()
        .filter(entry -> entry.getValue() != null && entry.getValue().getPieceColour() == team)
        .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
  }

  private boolean kingHasNoPossibleSaving(
      Map<BoardPosition, List<BoardPosition>> piecesWithPossibleMoves, Board context) {
    for (Entry<BoardPosition, List<BoardPosition>> entry : piecesWithPossibleMoves.entrySet()) {
      // Fetch current piece to analyse
      BoardPosition pos = entry.getKey();
      List<BoardPosition> moveSet = entry.getValue();

      // Iterate over all of its possible moves
      for (BoardPosition possibleMove : moveSet) {
        Piece piece = context.pieceAt(pos);
        Board possibleBoardState = context.removePieceAt(pos).addPieceAt(possibleMove, piece);
        DefaultCheck check = new DefaultCheck(context.pieceAt(pos).getPieceColour());

        // If after executing the move is not in check anymore, return false. Return true otherwise
        if (!check.isValidRule(possibleBoardState)) {
          return false;
        }
      }
    }
    return true;
  }
}
