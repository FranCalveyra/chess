package edu.austral.dissis.common.utils.move;

import edu.austral.dissis.chess.piece.movement.type.ChessPieceType;
import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.piece.Piece;
import edu.austral.dissis.common.promoters.Promoter;
import edu.austral.dissis.common.utils.Pair;
import edu.austral.dissis.common.utils.result.playresult.PieceTaken;
import edu.austral.dissis.common.utils.result.playresult.PlayResult;
import edu.austral.dissis.common.utils.result.playresult.PromotedPiece;
import edu.austral.dissis.common.utils.result.playresult.ValidPlay;
import java.awt.Color;

public class MoveExecutor {
  public Pair<Board, PlayResult> executeMove(
      BoardPosition oldPos, BoardPosition newPos, Board board, Promoter promoter) {
    // Now, move the piece. Take piece in newPos whether exists
    Piece piece = board.pieceAt(oldPos);
    Board newBoard;
    Piece pieceToTake = board.pieceAt(newPos);
    if (pieceToTake != null) {
      newBoard =
          board
              .removePieceAt(newPos)
              .removePieceAt(oldPos)
              .addPieceAt(newPos, piece.changeMoveState());
      Pair<Board, PlayResult> pair = new Pair<>(newBoard, new PieceTaken());
      return promoteIfAble(pair, newPos, piece.getPieceColour(), promoter);
    } else {
      newBoard = board.removePieceAt(oldPos).addPieceAt(newPos, piece.changeMoveState());
      Pair<Board, PlayResult> pair = new Pair<>(newBoard, new ValidPlay());
      return promoteIfAble(pair, newPos, piece.getPieceColour(), promoter);
    }
  }

  private Pair<Board, PlayResult> promoteIfAble(
      Pair<Board, PlayResult> pair, BoardPosition boardPosition, Color color, Promoter promoter) {
    Board board = pair.first();
    if (promoter.canPromote(boardPosition, board) || promoter.hasToPromote(board, color)) {
      return new Pair<>(
          promoter.promote(boardPosition, ChessPieceType.QUEEN, board), new PromotedPiece());
    }
    return pair;
  }
}
