package edu.austral.dissis.chess.providers;

import static edu.austral.dissis.chess.piece.movement.type.ChessPieceType.ARCHBISHOP;
import static edu.austral.dissis.chess.piece.movement.type.ChessPieceType.BISHOP;
import static edu.austral.dissis.chess.piece.movement.type.ChessPieceType.CHANCELLOR;
import static edu.austral.dissis.chess.piece.movement.type.ChessPieceType.KING;
import static edu.austral.dissis.chess.piece.movement.type.ChessPieceType.KNIGHT;
import static edu.austral.dissis.chess.piece.movement.type.ChessPieceType.PAWN;
import static edu.austral.dissis.chess.piece.movement.type.ChessPieceType.QUEEN;
import static edu.austral.dissis.chess.piece.movement.type.ChessPieceType.ROOK;

import edu.austral.dissis.checkers.piece.movement.CheckersType;
import edu.austral.dissis.chess.piece.movement.type.Castling;
import edu.austral.dissis.chess.piece.movement.type.ChessPieceType;
import edu.austral.dissis.chess.piece.movement.type.KnightMovement;
import edu.austral.dissis.chess.piece.movement.type.PawnFirstMove;
import edu.austral.dissis.chess.piece.movement.type.PawnMovement;
import edu.austral.dissis.chess.piece.movement.type.PawnTaking;
import edu.austral.dissis.common.piece.Piece;
import edu.austral.dissis.common.piece.PieceType;
import edu.austral.dissis.common.piece.movement.type.DiagonalMovement;
import edu.austral.dissis.common.piece.movement.type.HorizontalMovement;
import edu.austral.dissis.common.piece.movement.type.VerticalMovement;
import java.awt.Color;
import java.util.List;
import java.util.Random;
import org.jetbrains.annotations.NotNull;

public class ChessPieceProvider {

  public Piece provide(Color pieceColour, PieceType type) {
    String colorName = pieceColour == Color.WHITE ? "white" : "black";
    int randInt = new Random().nextInt();
    switch (type) {
      case KING:
        return new Piece(
            List.of(
                new HorizontalMovement(1),
                new VerticalMovement(1),
                new DiagonalMovement(1),
                new Castling()),
            pieceColour,
            type,
            getId(type, randInt, colorName));
      case ROOK:
        return new Piece(
            List.of(new HorizontalMovement(), new VerticalMovement()),
            pieceColour,
            type,
            getId(type, randInt, colorName));
      case QUEEN:
        return new Piece(
            List.of(new HorizontalMovement(), new VerticalMovement(), new DiagonalMovement()),
            pieceColour,
            type,
            getId(type, randInt, colorName));
      case PAWN:
        return new Piece(
            List.of(new PawnMovement(), new PawnTaking(), new PawnFirstMove()),
            pieceColour,
            type,
            getId(type, randInt, colorName));
      case BISHOP:
        return new Piece(
            List.of(new DiagonalMovement()), pieceColour, type, getId(type, randInt, colorName));
      case KNIGHT:
        return new Piece(
            List.of(new KnightMovement()), pieceColour, type, getId(type, randInt, colorName));
      case ARCHBISHOP:
        return new Piece(
            List.of(new DiagonalMovement(), new KnightMovement()),
            pieceColour,
            type,
            getId(type, randInt, colorName));
      case CHANCELLOR:
        return new Piece(
            List.of(new KnightMovement(), new VerticalMovement(), new HorizontalMovement()),
            pieceColour,
            type,
            getId(type, randInt, colorName));
      default:
        throw new IllegalArgumentException();
    }
  }

  // Implement in both
  public static @NotNull String getId(PieceType type, int randInt, String colorName) {
    if (type instanceof ChessPieceType chessPieceType) {
      return randInt + colorName + " " + chessPieceType.name().toLowerCase();
    } else if (type instanceof CheckersType checkersType) {
      return randInt + colorName + " " + checkersType.name().toLowerCase();
    }
    return randInt + colorName;
  }
}
