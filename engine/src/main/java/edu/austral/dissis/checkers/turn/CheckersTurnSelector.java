package edu.austral.dissis.checkers.turn;

import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.piece.Piece;
import edu.austral.dissis.common.piece.movement.type.PieceMovement;
import edu.austral.dissis.common.turn.TurnSelector;
import edu.austral.dissis.common.utils.move.BoardPosition;
import edu.austral.dissis.common.utils.move.GameMove;
import edu.austral.dissis.common.utils.result.playresult.PieceTaken;
import edu.austral.dissis.common.utils.result.playresult.PlayResult;
import java.awt.Color;

import static edu.austral.dissis.common.utils.AuxStaticMethods.getAttackingMoves;
import static edu.austral.dissis.common.utils.AuxStaticMethods.getTakingMove;

public class CheckersTurnSelector implements TurnSelector {
  private final Color currentTurn;

  public CheckersTurnSelector() {
    currentTurn = Color.BLACK;
  }

  private CheckersTurnSelector(Color currentTurn) {
    this.currentTurn = currentTurn;
  }

  @Override
  public Color getCurrentTurn() {
    return currentTurn;
  }

  @Override
  public TurnSelector changeTurn(GameMove lastMove, Board afterMoveBoard, PlayResult result) {
    return result.getClass() == PieceTaken.class && stillHaveAttackingMoves(lastMove.to(), afterMoveBoard)
        ? this
        : new CheckersTurnSelector(currentTurn == Color.RED ? Color.BLACK : Color.RED);
  }

  private boolean stillHaveAttackingMoves(BoardPosition boardPosition, Board afterMoveBoard) {
    PieceMovement pieceTakingMove = getTakingMove(afterMoveBoard.pieceAt(boardPosition));
    return pieceTakingMove != null && !getAttackingMoves(boardPosition, pieceTakingMove, afterMoveBoard).isEmpty();
  }
}
