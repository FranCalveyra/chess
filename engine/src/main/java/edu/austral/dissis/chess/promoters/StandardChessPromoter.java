package edu.austral.dissis.chess.promoters;

import edu.austral.dissis.chess.piece.movement.type.ChessPieceType;
import edu.austral.dissis.chess.providers.ChessPieceProvider;
import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.piece.Piece;
import edu.austral.dissis.common.piece.PieceType;
import edu.austral.dissis.common.promoters.Promoter;
import edu.austral.dissis.common.utils.move.BoardPosition;
import java.awt.Color;
import java.util.stream.IntStream;

/** Only promotes Pawns. */
public class StandardChessPromoter implements Promoter {

  @Override
  public boolean hasToPromote(Board context, Color team) {
    return isAnyPawnPromotable(context, team);
  }

  @Override
  public boolean canPromote(BoardPosition position, Board context) {
    Piece piece = context.pieceAt(position);
    int promoteRow = piece.getPieceColour() == Color.BLACK ? 0 : context.getRows() - 1;
    return piece.getType() == ChessPieceType.PAWN && position.getRow() == promoteRow;
  }

  @Override
  public Board promote(BoardPosition position, PieceType type, Board context) {
    // Fetch initial piece
    Piece initialPiece = context.pieceAt(position);
    // Get the piece to promote
    Piece pieceToPromoteTo = new ChessPieceProvider().provide(initialPiece.getPieceColour(), type);
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

  // Private stuff
  // If there's any pawn promotable in the board (in the last row, promote it)
  private boolean isAnyPawnPromotable(Board context, Color team) {
    // Changes the row to check depending on the analysed color. If WHITE, checks the last row;
    // else, checks the first
    int rowToCheck = team == Color.WHITE ? context.getRows() - 1 : 0;
    return IntStream.range(0, context.getColumns())
        .mapToObj(j -> new BoardPosition(rowToCheck, j))
        .map(context::pieceAt)
        .anyMatch(
            piece ->
                piece != null
                    && piece.getType() == ChessPieceType.PAWN
                    && piece.getPieceColour() == team);
  }
}
