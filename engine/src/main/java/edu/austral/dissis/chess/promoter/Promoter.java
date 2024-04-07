package edu.austral.dissis.chess.promoter;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.PieceType;
import edu.austral.dissis.chess.utils.Position;
import java.awt.Color;

public interface Promoter {
  boolean hasToPromote(Board context, Color team); // Check all first and last row for pawns

  boolean canPromote(Position position, Board context);

  void promote(Position position, PieceType type, Board context);
}
