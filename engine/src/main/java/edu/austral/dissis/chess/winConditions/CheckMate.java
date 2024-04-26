package edu.austral.dissis.chess.winConditions;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.piece.PieceType;
import edu.austral.dissis.chess.utils.Position;
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
    return checkmate(context.createCopy(), team);
  }

  private boolean checkmate(Board context, Color team) {
    if (!new DefaultCheck(team).isValidRule(context)) {
      return false;
    }
    Map<Position, Piece> teamPieces = getPiecesByColor(context, team);
    Map<Position, List<Position>> piecesWithPossibleMoves = getPieceMovesMap(teamPieces, context);
    return kingHasNoPossibleSaving(piecesWithPossibleMoves, context);
  }


  private Map<Position, List<Position>> getPieceMovesMap(Map<Position, Piece> teamPieces, Board context) {
    Map<Position, List<Position>> piecesWithPossibleMoves = new HashMap<>();
      teamPieces.forEach((pos, piece) -> {
          List<Position> moveSet = piece.getMoveSet(pos, context);
          if (moveSet.isEmpty()) {
              return;
          }
          piecesWithPossibleMoves.put(pos, moveSet);
      });
    return piecesWithPossibleMoves;
  }

  private Map<Position, Piece> getPiecesByColor(Board context, Color team) {
    Map<Position, Piece> map = context.getPiecesAndPositions();
    return map.entrySet().stream().filter(entry -> entry.getValue() != null && entry.getValue().getPieceColour() == team && entry.getValue().getType() != PieceType.KING)
            .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
  }

  private boolean kingHasNoPossibleSaving(Map<Position, List<Position>> piecesWithPossibleMoves, Board context) {
    for (Entry<Position, List<Position>> entry : piecesWithPossibleMoves.entrySet()) {
      //Fetch current piece to analyse
      Position pos = entry.getKey();
      List<Position> moveSet = entry.getValue();
      //Iterate over all of its possible moves
      for (Position possibleMove : moveSet) {
        Board possibleBoardState = context.updatePiecePosition(pos, possibleMove);
        DefaultCheck check = new DefaultCheck(context.pieceAt(pos).getPieceColour());

        //If after executing the move is not in check anymore, return false. Return true otherwise (going through all the pieces)
        if(!check.isValidRule(possibleBoardState)){
          return false;
        }
      }
    }
    return true;
  }

}
