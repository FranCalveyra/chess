package edu.austral.dissis.common.utils.move;

import edu.austral.dissis.chess.piece.movement.type.ChessPieceType;
import edu.austral.dissis.chess.promoters.Promoter;
import edu.austral.dissis.chess.utils.Pair;
import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.piece.Piece;
import edu.austral.dissis.common.piece.PieceType;
import edu.austral.dissis.common.utils.result.PlayResult;
import edu.austral.dissis.common.utils.result.PieceTaken;
import edu.austral.dissis.common.utils.result.ValidPlay;

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
              .addPieceAt(newPos, piece.hasNotMoved() ? piece.changeMoveState() : piece);
      newBoard = promoteIfAble(newBoard, newPos, piece.getPieceColour(), promoter);
      return new Pair<>(newBoard, new PieceTaken(pieceToTake));
    } else {
      newBoard =
          board
              .removePieceAt(oldPos)
              .addPieceAt(newPos, piece.hasNotMoved() ? piece.changeMoveState() : piece);
      newBoard = promoteIfAble(newBoard, newPos, piece.getPieceColour(), promoter);
      return new Pair<>(newBoard, new ValidPlay());
    }
  }

  private Board promoteIfAble(
      Board board, BoardPosition boardPosition, Color color, Promoter promoter) {
    if (promoter.canPromote(boardPosition, board) || promoter.hasToPromote(board, color)) {
      return promoter.promote(boardPosition, ChessPieceType.QUEEN, board);
    }
    return board;
  }
}
