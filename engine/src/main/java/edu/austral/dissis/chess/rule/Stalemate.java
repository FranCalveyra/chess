package edu.austral.dissis.chess.rule;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.piece.PieceType;
import edu.austral.dissis.chess.utils.Position;
import edu.austral.dissis.chess.utils.UnallowedMoveException;
import java.awt.Color;
import java.util.List;
import java.util.Map;

public class Stalemate implements WinCondition {
  private final Color team;
  public Stalemate(Color team){
    this.team = team;
  }

  // Should handle "tie"
  @Override
  public boolean isValidRule(Board context) throws UnallowedMoveException {
    return bruteForceMovementCheck(context, team);
  }

  private boolean bruteForceMovementCheck(Board context, Color team) throws UnallowedMoveException {
    for (Map.Entry<Position, Piece> entry : context.getPiecesAndPositions().entrySet()) {
      Position actualPosition = entry.getKey();
      Piece currentPiece = entry.getValue();
      if (hasValidMove(actualPosition, currentPiece, context, team)) {
        return false;
      }
    }
    return true;
  }

  private boolean hasValidMove(Position position, Piece piece, Board context, Color team)
      throws UnallowedMoveException {
    if (piece.getPieceColour() != team) {
      return false;
    }
    List<Position> possibleMoves = piece.getMoveSet(position, context);
    Check check = new DefaultCheck(team);
    if (possibleMoves.isEmpty()) {
      return true;
    }
    return possibleMovesPutGameInCheck(possibleMoves, check, context, position);
  }

  private boolean possibleMovesPutGameInCheck(
      List<Position> possibleMoves, Check check, Board context, Position initialPosition)
      throws UnallowedMoveException {
    for (Position move : possibleMoves) {
      Board possibleBoard = context.updatePiecePosition(initialPosition, move);
      if (!check.isValidRule(possibleBoard)) {
        return false;
      }
    }
    return true;
  }
}
