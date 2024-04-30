package edu.austral.dissis.chess.providers;

import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.piece.PieceType;
import edu.austral.dissis.chess.piece.movement.Castling;
import edu.austral.dissis.chess.piece.movement.DiagonalMovement;
import edu.austral.dissis.chess.piece.movement.HorizontalMovement;
import edu.austral.dissis.chess.piece.movement.KingMovement;
import edu.austral.dissis.chess.piece.movement.KnightMovement;
import edu.austral.dissis.chess.piece.movement.PawnFirstMove;
import edu.austral.dissis.chess.piece.movement.PawnMovement;
import edu.austral.dissis.chess.piece.movement.PawnTaking;
import edu.austral.dissis.chess.piece.movement.PieceMovement;
import edu.austral.dissis.chess.piece.movement.VerticalMovement;
import java.awt.Color;
import java.util.List;
import java.util.Random;

public class PieceProvider {

  public Piece provide(Color pieceColour, PieceType type) {
    PieceMovement castling = new Castling();
    String colorName = pieceColour == Color.WHITE ? "white" : "black";
    int randInt = new Random().nextInt();
    switch (type) {
      case KING:
        return new Piece(
            List.of(new KingMovement(), castling),
            pieceColour,
            type,
            randInt + colorName + " " + type.name().toLowerCase());
      case ROOK:
        return new Piece(
            List.of(new HorizontalMovement(), new VerticalMovement()),
            pieceColour,
            type,
            randInt + colorName + " " + type.name().toLowerCase());
      case QUEEN:
        return new Piece(
            List.of(new HorizontalMovement(), new VerticalMovement(), new DiagonalMovement()),
            pieceColour,
            type,
            randInt + colorName + " " + type.name().toLowerCase());
      case PAWN:
        return new Piece(
            List.of(new PawnMovement(), new PawnTaking(), new PawnFirstMove()),
            pieceColour,
            type,
            randInt + colorName + " " + type.name().toLowerCase());
      case BISHOP:
        return new Piece(
            List.of(new DiagonalMovement()),
            pieceColour,
            type,
            randInt + colorName + " " + type.name().toLowerCase());
      case KNIGHT:
        return new Piece(
            List.of(new KnightMovement()),
            pieceColour,
            type,
            randInt + colorName + " " + type.name().toLowerCase());
      default:
        throw new IllegalArgumentException();
    }
  }
}
