package edu.austral.dissis.chess.promoter;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.piece.PieceType;
import edu.austral.dissis.chess.provider.PieceProvider;
import edu.austral.dissis.chess.utils.Position;
import java.awt.Color;
import java.util.List;

public class StandardPromoter implements Promoter {

  @Override
  public boolean hasToPromote(Board context, Color team) {
    return isAnyPawnPromotable(context, team);
  }

  @Override
  public boolean canPromote(Position position, Board context) {
    Piece piece = context.pieceAt(position);
    if (piece == null) {
      return false;
    }
    int promoteRow = piece.getPieceColour() == Color.BLACK ? 0 : context.getRows() - 1;
    return piece.getType() == PieceType.PAWN && position.getRow() == promoteRow;
  }

  @Override
  public Board promote(Position position, PieceType type, Board context) {
    if(takenPiecesDontIncludeType(context.getTakenPieces(), type)){
      return context;
    }
    Piece initialPiece = context.pieceAt(position);
    Piece piece = new PieceProvider().get(initialPiece.getPieceColour(), type);
    Piece actualPiece =
        new Piece(piece.getMovements(), piece.getPieceColour(), type, !initialPiece.hasNotMoved());

    List<Piece> updatedTakenPieces = context.getTakenPieces();
    removeRevivedPiece(updatedTakenPieces, type);

    Board newBoard = context.removePieceAt(position).addPieceAt(position, actualPiece);
      return new Board(newBoard.getPiecesAndPositions(), newBoard.getSelector(),newBoard.getRows(), newBoard.getColumns(),
              updatedTakenPieces, newBoard.getCurrentTurn(), newBoard.getPromoter());
      /*
      Map<Position, Piece> pieces,
  TurnSelector selector,
  int rows,
  int columns,
  List<Piece> takenPieces,
  Color currentTurn,
  Promoter promoter
       */
  }

  //Private stuff
  private boolean isAnyPawnPromotable(Board context, Color team) {
    int rowToCheck = team == Color.WHITE ? context.getRows() - 1 : 0;
    for (int j = 0; j < context.getColumns(); j++) {
      Piece pieceAt = context.pieceAt(new Position(rowToCheck, j));
      if (pieceAt != null
              && pieceAt.getType() == PieceType.PAWN
              && pieceAt.getPieceColour() == team) {
        return true;
      }
    }
    return false;
  }

  private void removeRevivedPiece(List<Piece> updatedTakenPieces, PieceType type) {
    if(!updatedTakenPieces.isEmpty() || takenPiecesDontIncludeType(updatedTakenPieces, type)) {
      return;
    }
      updatedTakenPieces.removeIf(piece -> piece.getType() == type);
  }

  private boolean takenPiecesDontIncludeType(List<Piece> takenPieces, PieceType type) {
    for(Piece piece: takenPieces){
      if(piece.getType() == type){
        return false;
      }
    }
    return true;
  }

}
