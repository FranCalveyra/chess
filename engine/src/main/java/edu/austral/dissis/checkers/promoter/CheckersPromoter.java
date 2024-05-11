package edu.austral.dissis.checkers.promoter;

import edu.austral.dissis.checkers.piece.movement.CheckersType;
import edu.austral.dissis.checkers.providers.CheckersPieceProvider;
import edu.austral.dissis.chess.piece.movement.type.ChessPieceType;
import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.piece.Piece;
import edu.austral.dissis.common.piece.PieceType;
import edu.austral.dissis.common.promoters.Promoter;
import edu.austral.dissis.common.utils.move.BoardPosition;
import java.awt.Color;
import java.util.stream.IntStream;

public class CheckersPromoter implements Promoter {
  @Override
  public boolean hasToPromote(Board context, Color team) {
    return isAnyManPromotable(context, team);
  }

  @Override
  public boolean canPromote(BoardPosition position, Board context) {
    Piece piece = context.pieceAt(position);
    int promoteRow = piece.getPieceColour() == Color.RED ? 0 : context.getRows() - 1;
    return piece.getType() == CheckersType.MAN && position.getRow() == promoteRow;
  }

  @Override
  public Board promote(BoardPosition position, PieceType type, Board context) {
    // Fetch initial piece
    Piece initialPiece = context.pieceAt(position);
    // Get the piece to promote
    if (type == ChessPieceType.QUEEN) {
      type = CheckersType.KING;
    }
    Piece pieceToPromoteTo =
        new CheckersPieceProvider().provideCheckersPiece(initialPiece.getPieceColour(), type);
    Piece promotedPiece =
        new Piece(
            pieceToPromoteTo.getMovements(),
            pieceToPromoteTo.getPieceColour(),
            type,
            initialPiece.getMoveCounter(),
            initialPiece.getId());
    // Update the board
    return context.removePieceAt(position).addPieceAt(position, promotedPiece);
  }

  // If there's any pawn promotable in the board (in the last row, promote it)
  private boolean isAnyManPromotable(Board context, Color team) {
    // Changes the row to check depending on the analysed color. If WHITE, checks the last row;
    // else, checks the first
    int rowToCheck = team == Color.RED ? context.getRows() - 1 : 0;
    return IntStream.range(0, context.getColumns())
        .mapToObj(j -> new BoardPosition(rowToCheck, j))
        .map(context::pieceAt)
        .anyMatch(
            piece ->
                piece != null
                    && piece.getType() == CheckersType.MAN
                    && piece.getPieceColour() == team);
  }
}
