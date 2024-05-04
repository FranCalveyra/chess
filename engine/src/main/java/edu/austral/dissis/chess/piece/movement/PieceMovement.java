package edu.austral.dissis.chess.piece.movement;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.utils.move.ChessMove;
import edu.austral.dissis.chess.utils.move.ChessPosition;
import java.util.ArrayList;
import java.util.List;

public interface PieceMovement {

  boolean isValidMove(ChessMove move, Board context);

  default List<ChessPosition> getPossiblePositions(ChessPosition currentPos, Board context) {
    List<ChessPosition> possibleChessPositions = new ArrayList<>();
    for (int i = 0; i < context.getRows(); i++) {
      for (int j = 0; j < context.getColumns(); j++) {
        ChessPosition positionToMove = new ChessPosition(i, j);
        ChessMove moveToDo = new ChessMove(currentPos, positionToMove);
        if (isValidMove(moveToDo, context) && !possibleChessPositions.contains(positionToMove)) {
          possibleChessPositions.add(positionToMove);
        }
      }
    }
    return possibleChessPositions;
  }

  default List<ChessMove> getMovesToExecute(ChessMove move, Board context) {
    return List.of(move);
  }
}
