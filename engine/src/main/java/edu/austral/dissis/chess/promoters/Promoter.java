package edu.austral.dissis.chess.promoters;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.PieceType;
import edu.austral.dissis.chess.utils.ChessPosition;
import java.awt.Color;

public interface Promoter {
  boolean hasToPromote(Board context, Color team); // Check the promotion row of a given team

  boolean canPromote(
      ChessPosition position, Board context); // Check if piece at given position is promotable

  Board promote(ChessPosition position, PieceType type, Board context);
}
