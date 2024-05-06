package edu.austral.dissis.chess.rules.winconds;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.utils.move.ChessPosition;
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
    Map<ChessPosition, Piece> teamPieces = getPiecesByColor(context, team);
    Map<ChessPosition, List<ChessPosition>> piecesWithPossibleMoves =
        getPieceMovesMap(teamPieces, context);
    return kingHasNoPossibleSaving(piecesWithPossibleMoves, context);
  }

  private Map<ChessPosition, List<ChessPosition>> getPieceMovesMap(
      Map<ChessPosition, Piece> teamPieces, Board context) {
    Map<ChessPosition, List<ChessPosition>> piecesWithPossibleMoves = new HashMap<>();
    teamPieces.forEach(
        (pos, piece) -> {
          List<ChessPosition> moveSet = piece.getMoveSet(pos, context);
          if (moveSet.isEmpty()) {
            return;
          }
          piecesWithPossibleMoves.put(pos, moveSet);
        });
    return piecesWithPossibleMoves;
  }

  private Map<ChessPosition, Piece> getPiecesByColor(Board context, Color team) {
    Map<ChessPosition, Piece> map = context.getPiecesAndPositions();
    return map.entrySet().stream()
        .filter(entry -> entry.getValue() != null && entry.getValue().getPieceColour() == team)
        .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
  }

  private boolean kingHasNoPossibleSaving(
      Map<ChessPosition, List<ChessPosition>> piecesWithPossibleMoves, Board context) {
    for (Entry<ChessPosition, List<ChessPosition>> entry : piecesWithPossibleMoves.entrySet()) {
      // Fetch current piece to analyse
      ChessPosition pos = entry.getKey();
      List<ChessPosition> moveSet = entry.getValue();

      // Iterate over all of its possible moves
      for (ChessPosition possibleMove : moveSet) {
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
