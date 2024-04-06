package edu.austral.dissis.chess.provider;

import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.piece.PieceType;
import edu.austral.dissis.chess.rule.Castling;
import edu.austral.dissis.chess.rule.movement.*;
import java.awt.Color;
import java.util.List;

public class PieceProvider {

  public Piece get(Color pieceColour, PieceType type) {
    PieceMovementRule castling = new Castling();
    return switch (type) {
      case KING -> new Piece(List.of(new KingMovementRule()), pieceColour, type);
      case ROOK -> new Piece(List.of(new RookMovementRule()), pieceColour, type);
      case QUEEN -> new Piece(List.of(new QueenMovementRule()), pieceColour, type);
      case PAWN ->
          new Piece(
              List.of(new PawnMovementRule(), new PawnTakingRule(), new PawnFirstMoveRule()),
              pieceColour,
              type);
      case BISHOP -> new Piece(List.of(new DiagonalMovementRule()), pieceColour, type);
      case KNIGHT -> new Piece(List.of(new KnightMovementRule()), pieceColour, type);
      default -> throw new IllegalArgumentException();
    };
  }
}
