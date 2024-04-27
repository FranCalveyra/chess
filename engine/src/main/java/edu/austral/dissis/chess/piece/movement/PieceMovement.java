package edu.austral.dissis.chess.piece.movement;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.utils.ChessPosition;
import java.util.ArrayList;
import java.util.List;

public interface PieceMovement {

  boolean isValidMove(ChessPosition oldPos, ChessPosition newPos, Board context);

  default List<ChessPosition> getPossibleMoves(ChessPosition oldPos, Board context) {
    List<ChessPosition> possibleChessPositions = new ArrayList<>();
    for (int i = 0; i < context.getRows(); i++) {
      for (int j = 0; j < context.getColumns(); j++) {
        ChessPosition currentPos = new ChessPosition(i, j);
        if (isValidMove(oldPos, currentPos, context)
            && !possibleChessPositions.contains(currentPos)
            && context.pieceAt(currentPos) == null) {
          possibleChessPositions.add(currentPos);
        }
      }
    }
    return possibleChessPositions;
  }
}
