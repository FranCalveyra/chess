package edu.austral.dissis.chess.rule;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
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
    Map<Piece, List<Position>> piecesWithPossibleMoves = getPieceMovesMap(teamPieces, context);
    System.out.println(context);
    System.out.println(piecesWithPossibleMoves);
    return noPossibleSaving(piecesWithPossibleMoves, context);
  }
  /*
  [BL R, BL K, BL B, null, BL K, BL B, BL K, BL R]
  [BL P, BL P, BL P, BL P, null, BL P, BL P, BL P]
  [null, null, null, null, null, null, null, null]
  [null, null, null, null, BL P, null, null, null]
  [null, null, null, null, null, null, WH P, BL Q]
  [null, null, null, null, null, WH P, null, null]
  [WH P, WH P, WH P, WH P, WH P, null, null, WH P]
  [WH R, WH K, WH B, WH Q, WH K, WH B, WH K, WH R]
   */

  private boolean noPossibleSaving(Map<Piece, List<Position>> piecePossibleMoves, Board context)
      throws UnallowedMoveException {
    for (Entry<Piece, List<Position>> entry : piecePossibleMoves.entrySet()) {
      Position currentPiecePosition = getPiecePosition(entry.getKey(), context);
      DefaultCheck check = new DefaultCheck(entry.getKey().getPieceColour());
      for (Position possibleMove : entry.getValue()) {
        Board newBoard = new Board(context.getActivePiecesAndPositions(), context.getSelector());
        newBoard.updatePiecePosition(possibleMove, newBoard.pieceAt(currentPiecePosition));
        if (!check.isValidRule(newBoard)) {
          return false;
        }
      }
    }
    return true;
  }

  private Position getPiecePosition(Piece piece, Board context) {
    for (Map.Entry<Position, Piece> entry : context.getActivePiecesAndPositions().entrySet()) {
      if (entry.getValue() == piece && piece != null) {
        return entry.getKey();
      }
    }
    return null;
  }

  private Map<Piece, List<Position>> getPieceMovesMap(
      Map<Position, Piece> teamPieces, Board context) {
    Map<Piece, List<Position>> piecePossibleMoves = new HashMap<>();
    for (Entry<Position, Piece> entry : teamPieces.entrySet()) {
      Piece piece = entry.getValue();
      piecePossibleMoves.put(piece, piece.getMoveSet(entry.getKey(), context));
    }
    return piecePossibleMoves;
  }

  private Map<Position, Piece> getPiecesByColor(Board context, Color team) {
    Map<Position, Piece> teamPieces = new HashMap<>();
    for (Entry<Position, Piece> entry : context.getActivePiecesAndPositions().entrySet()) {
      if (entry.getValue().getPieceColour() == team) {
        teamPieces.put(entry.getKey(), entry.getValue());
      }
    }
    return teamPieces;
  }

  // IF NOT CHECK FALSE, ELSE:
  // MOVE ALL TEAMMATES TO ALL THEIR POSSIBLE POSITIONS TO CHECK IF KING STILL IN CHECK ☠☠☠☠☠

}
