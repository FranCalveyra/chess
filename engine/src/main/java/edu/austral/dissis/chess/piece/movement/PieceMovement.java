package edu.austral.dissis.chess.piece.movement;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.utils.Position;
import java.util.ArrayList;
import java.util.List;

public interface PieceMovement {

  boolean isValidMove(Position oldPos, Position newPos, Board context);

  default List<Position> getPossibleMoves(Position oldPos, Board context) {
    List<Position> possiblePositions = new ArrayList<>();
    for (int i = 0; i < context.getRows(); i++) {
      for (int j = 0; j < context.getColumns(); j++) {
        Position currentPos = new Position(i, j);
        if (isValidMove(oldPos, currentPos, context)
            && !possiblePositions.contains(currentPos)
            && context.pieceAt(currentPos) == null) {
          possiblePositions.add(currentPos);
        }
      }
    }
    return possiblePositions;
  }
}
