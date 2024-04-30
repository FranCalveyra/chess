package edu.austral.dissis.chess.utils;

import static edu.austral.dissis.chess.utils.ChessMoveResult.INVALID_MOVE;
import static edu.austral.dissis.chess.utils.ChessMoveResult.PIECE_TAKEN;
import static edu.austral.dissis.chess.utils.ChessMoveResult.VALID_MOVE;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.piece.PieceType;
import edu.austral.dissis.chess.promoters.Promoter;
import java.awt.Color;

public class MoveExecutor {
  public Pair<Board, ChessMoveResult> executeMove(
      ChessPosition oldPos, ChessPosition newPos, Board board, Promoter promoter) {
    // Now, move the piece. Take piece in newPos whether exists
    Piece piece = board.pieceAt(oldPos);
    Board newBoard;
    Piece pieceToTake = board.pieceAt(newPos);
    if (pieceToTake != null) {
      if (pieceToTake.getPieceColour() == piece.getPieceColour()) {
        return new Pair<>(board, INVALID_MOVE); //TODO: REMOVE
      } else {
        newBoard =
            board
                .removePieceAt(newPos)
                .removePieceAt(oldPos)
                .addPieceAt(newPos, !piece.hasMoved() ? piece.changeMoveState() : piece);
        newBoard = promoteIfAble(newBoard, newPos, piece.getPieceColour(), promoter);
        return new Pair<>(newBoard, PIECE_TAKEN);
      }
    } else {
      newBoard =
          board
              .removePieceAt(oldPos)
              .addPieceAt(newPos, !piece.hasMoved() ? piece.changeMoveState() : piece);
      newBoard = promoteIfAble(newBoard, newPos, piece.getPieceColour(), promoter);
      return new Pair<>(newBoard, VALID_MOVE);
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
