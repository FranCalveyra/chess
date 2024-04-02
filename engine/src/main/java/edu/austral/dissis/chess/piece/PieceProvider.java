package edu.austral.dissis.chess.piece;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.rule.movement.*;
import java.awt.*;
import java.util.List;

public class PieceProvider {
  private final Board context;

  public PieceProvider(Board context) {
    this.context = context;
  }

  public Piece get(Color pieceColour, PieceType type) {
    return switch (type) {
      case KING -> new Piece(List.of(new KingMovementRule()), pieceColour, type);
      case ROOK -> new Piece(List.of(new RookMovementRule()), pieceColour, type);
      case QUEEN -> new Piece(List.of(new QueenMovementRule()), pieceColour, type);
      case PAWN ->
          new Piece(
              List.of(new PawnMovementRule(), new PawnTakingRule(context)), pieceColour, type);
      case BISHOP -> new Piece(List.of(new DiagonalMovementRule()), pieceColour, type);
      case KNIGHT -> new Piece(List.of(new KnightMovementRule()), pieceColour, type);
    };
  }
}
