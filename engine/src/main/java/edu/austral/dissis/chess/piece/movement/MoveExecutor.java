package edu.austral.dissis.chess.piece.movement;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.piece.PieceType;
import edu.austral.dissis.chess.promoters.Promoter;
import edu.austral.dissis.chess.utils.ChessMove;
import edu.austral.dissis.chess.utils.ChessMoveResult;
import edu.austral.dissis.chess.utils.ChessPosition;
import edu.austral.dissis.chess.utils.Pair;

import java.awt.*;

import static edu.austral.dissis.chess.utils.ChessMoveResult.*;

public class MoveExecutor {
    public Pair<Board, ChessMoveResult> executeMove(ChessPosition oldPos, ChessPosition newPos, Board board, Promoter promoter) {
        // Now, move the piece. Take piece in newPos whether exists
        Piece piece = board.pieceAt(oldPos);
        if(piece == null){
            return new Pair<>(board, INVALID_MOVE);
        }
        Board newBoard;
        Piece pieceToTake = board.pieceAt(newPos); // Check outside
        if (pieceToTake != null) {
            if (pieceToTake.getPieceColour() == piece.getPieceColour()) {
                return new Pair<>(board, INVALID_MOVE);
            } else {
                newBoard = board.removePieceAt(newPos).updatePiecePosition(oldPos, newPos);
                newBoard = promoteIfAble(newBoard, newPos, piece.getPieceColour(), promoter);
                return new Pair<>(newBoard, PIECE_TAKEN);
            }
        } else {
            newBoard =
                    board
                            .removePieceAt(oldPos)
                            .addPieceAt(newPos, piece.hasNotMoved() ? piece.changeMoveState() : piece);
            newBoard = promoteIfAble(newBoard, newPos, piece.getPieceColour(), promoter);
            return new Pair<>(newBoard, VALID_MOVE);
        }
    }

    private Board promoteIfAble(Board board, ChessPosition chessPosition, Color color, Promoter promoter) {
        if (promoter.canPromote(chessPosition, board) || promoter.hasToPromote(board, color)) {
            return promoter.promote(chessPosition, PieceType.QUEEN, board);
        }
        return board;
    }
}
