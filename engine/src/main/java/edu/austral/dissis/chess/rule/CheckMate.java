package edu.austral.dissis.chess.rule;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.piece.PieceType;
import edu.austral.dissis.chess.utils.Position;
import edu.austral.dissis.chess.utils.UnallowedMoveException;
import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CheckMate implements WinCondition {
  private final Color team;

  public CheckMate(Color team) {
    this.team = team;
  }

  @Override
  public boolean isValidRule(Board context) throws UnallowedMoveException {
    return checkmate(context, team);
  }

  private boolean checkmate(Board context, Color team) throws UnallowedMoveException {
    if (!new DefaultCheck(team).isValidRule(context)) {
      return false;
    }
    Map<Position, Piece> teamPieces = getPiecesByColor(context, team);
    Map<Position, List<Position>> piecesWithPossibleMoves = getPieceMovesMap(teamPieces, context);
    return noPossibleSaving(piecesWithPossibleMoves, context);
  }

  private boolean noPossibleSaving(Map<Position, List<Position>> piecePossibleMoves, Board context)
      throws UnallowedMoveException {
    for (Entry<Position, List<Position>> entry : piecePossibleMoves.entrySet()) {
      Position currentPiecePosition = entry.getKey();
      Piece currentPiece = context.pieceAt(entry.getKey());
      DefaultCheck check = new DefaultCheck(currentPiece.getPieceColour());
      for (Position possibleMove : entry.getValue()) {
        Board newBoard =
            new Board(
                context.getPiecesAndPositions(), context.getSelector(), context.getPromoter());
        newBoard =
            newBoard.updatePiecePosition(currentPiecePosition, possibleMove, PieceType.QUEEN);
        if (newBoard.getCurrentTurn() != currentPiece.getPieceColour()) {
          newBoard =
              new Board(
                  context.getPiecesAndPositions(),
                  context.getSelector(),
                  context.getTakenPieces(),
                  context.changeTurn(currentPiece.getPieceColour()),
                  context.getPromoter());
        }
        if (!check.isValidRule(newBoard)) {
          return false;
        }
      }
    }
    return true;
  }

  private Map<Position, List<Position>> getPieceMovesMap(
      Map<Position, Piece> teamPieces, Board context) {
    Map<Position, List<Position>> piecePossibleMoves = new HashMap<>();
    for (Entry<Position, Piece> entry : teamPieces.entrySet()) {
      Position currentPosition = entry.getKey();
      piecePossibleMoves.put(
          currentPosition, context.pieceAt(currentPosition).getMoveSet(entry.getKey(), context));
    }
    return piecePossibleMoves;
  }

  private Map<Position, Piece> getPiecesByColor(Board context, Color team) {
    Map<Position, Piece> teamPieces = new HashMap<>();
    for (Entry<Position, Piece> entry : context.getPiecesAndPositions().entrySet()) {
      if (entry.getValue().getPieceColour() == team) {
        teamPieces.put(entry.getKey(), entry.getValue());
      }
    }
    return teamPieces;
  }

  // IF NOT CHECK FALSE, ELSE:
  // MOVE ALL TEAMMATES TO ALL THEIR POSSIBLE POSITIONS TO CHECK IF KING STILL IN CHECK ☠☠☠☠☠

}
