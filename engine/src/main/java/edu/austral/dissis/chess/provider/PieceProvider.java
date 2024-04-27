package edu.austral.dissis.chess.provider;

import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.piece.PieceType;
import edu.austral.dissis.chess.piece.movement.Castling;
import edu.austral.dissis.chess.piece.movement.DiagonalMovement;
import edu.austral.dissis.chess.piece.movement.KingMovement;
import edu.austral.dissis.chess.piece.movement.KnightMovement;
import edu.austral.dissis.chess.piece.movement.PawnFirstMove;
import edu.austral.dissis.chess.piece.movement.PawnMovement;
import edu.austral.dissis.chess.piece.movement.PawnTaking;
import edu.austral.dissis.chess.piece.movement.PieceMovement;
import edu.austral.dissis.chess.piece.movement.QueenMovement;
import edu.austral.dissis.chess.piece.movement.RookMovement;
import java.awt.Color;
import java.util.List;

public class PieceProvider {

  public Piece get(Color pieceColour, PieceType type) {
    PieceMovement castling = new Castling();
    return switch (type) {
      case KING -> new Piece(List.of(new KingMovement(), castling), pieceColour, type);
      case ROOK -> new Piece(List.of(new RookMovement()), pieceColour, type);
      case QUEEN -> new Piece(List.of(new QueenMovement()), pieceColour, type);
      case PAWN ->
          new Piece(
              List.of(new PawnMovement(), new PawnTaking(), new PawnFirstMove()),
              pieceColour,
              type);
      case BISHOP -> new Piece(List.of(new DiagonalMovement()), pieceColour, type);
      case KNIGHT -> new Piece(List.of(new KnightMovement()), pieceColour, type);
    };
  }
}
