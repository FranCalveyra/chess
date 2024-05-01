package edu.austral.dissis.chess.engine;

import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.utils.ChessPosition;
import java.util.Map;

public interface Board {
  MapBoard addPieceAt(ChessPosition pos, Piece piece);

  MapBoard removePieceAt(ChessPosition pos);

  Piece pieceAt(ChessPosition pos);

  Map<ChessPosition, Piece> getPiecesAndPositions();

  int getRows();

  int getColumns();
}
