package edu.austral.dissis.chess.piece.movement;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.utils.ChessMove;
import edu.austral.dissis.chess.utils.ChessPosition;
import java.util.ArrayList;
import java.util.List;

public interface PieceMovement {

  boolean isValidMove(ChessPosition oldPos, ChessPosition newPos, Board context);

  default List<ChessPosition> getPossiblePositions(ChessPosition currentPos, Board context) {
    List<ChessPosition> possibleChessPositions = new ArrayList<>();
    for (int i = 0; i < context.getRows(); i++) {
      for (int j = 0; j < context.getColumns(); j++) {
        ChessPosition positionToMove = new ChessPosition(i, j);
        if (isValidMove(currentPos, positionToMove, context)
            && !possibleChessPositions.contains(positionToMove)
            && context.pieceAt(positionToMove) == null) {
          possibleChessPositions.add(positionToMove);
        }
      }
    }
    return possibleChessPositions;
  }

  default List<ChessMove> getMovesToExecute(
      ChessPosition oldPos, ChessPosition newPos, Board context) {
    return List.of(new ChessMove(oldPos, newPos));
  }
}
