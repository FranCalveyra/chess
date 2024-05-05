package edu.austral.dissis.chess.utils;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.piece.PieceType;
import edu.austral.dissis.chess.promoters.Promoter;
import edu.austral.dissis.chess.utils.move.ChessPosition;
import edu.austral.dissis.chess.utils.result.ChessMoveResult;
import edu.austral.dissis.chess.utils.result.PieceTaken;
import edu.austral.dissis.chess.utils.result.ValidMove;
import java.awt.Color;

public class MoveExecutor {
  public Pair<Board, ChessMoveResult> executeMove(
      ChessPosition oldPos, ChessPosition newPos, Board board, Promoter promoter) {
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
      return new Pair<>(newBoard, new ValidMove());
    }
  }

  private Board promoteIfAble(
      Board board, ChessPosition chessPosition, Color color, Promoter promoter) {
    if (promoter.canPromote(chessPosition, board) || promoter.hasToPromote(board, color)) {
      return promoter.promote(chessPosition, PieceType.QUEEN, board);
    }
    return board;
  }
}
