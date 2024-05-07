package edu.austral.dissis.common.piece.movement.type;

import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.utils.move.BoardPosition;
import edu.austral.dissis.common.utils.move.GameMove;
import java.util.ArrayList;
import java.util.List;

public interface PieceMovement {

  boolean isValidMove(GameMove move, Board context);

  default List<BoardPosition> getPossiblePositions(BoardPosition currentPos, Board context) {
    List<BoardPosition> possibleBoardPositions = new ArrayList<>();
    for (int i = 0; i < context.getRows(); i++) {
      for (int j = 0; j < context.getColumns(); j++) {
        BoardPosition positionToMove = new BoardPosition(i, j);
        GameMove moveToDo = new GameMove(currentPos, positionToMove);
        if (isValidMove(moveToDo, context) && !possibleBoardPositions.contains(positionToMove)) {
          possibleBoardPositions.add(positionToMove);
        }
      }
    }
    return possibleBoardPositions;
  }

  default List<GameMove> getMovesToExecute(GameMove move, Board context) {
    return List.of(move);
  }
}
