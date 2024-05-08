package edu.austral.dissis.chess.providers;

import edu.austral.dissis.chess.piece.movement.type.Castling;
import edu.austral.dissis.chess.piece.movement.type.ChessPieceType;
import edu.austral.dissis.chess.piece.movement.type.PawnMovement;
import edu.austral.dissis.chess.piece.movement.type.PawnFirstMove;
import edu.austral.dissis.chess.piece.movement.type.PawnTaking;
import edu.austral.dissis.chess.piece.movement.type.KnightMovement;
import edu.austral.dissis.common.piece.Piece;
import edu.austral.dissis.common.piece.PieceType;
import edu.austral.dissis.common.piece.movement.type.DiagonalMovement;
import edu.austral.dissis.common.piece.movement.type.HorizontalMovement;
import edu.austral.dissis.common.piece.movement.type.VerticalMovement;
import java.awt.Color;
import java.util.List;
import java.util.Random;

import static edu.austral.dissis.chess.piece.movement.type.ChessPieceType.*;

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
            randInt + colorName + " " + ((ChessPieceType) type).name().toLowerCase());
      case ROOK:
        return new Piece(
            List.of(new HorizontalMovement(), new VerticalMovement()),
            pieceColour,
            type,
            randInt + colorName + " " + ((ChessPieceType) type).name().toLowerCase());
      case QUEEN:
        return new Piece(
            List.of(new HorizontalMovement(), new VerticalMovement(), new DiagonalMovement()),
            pieceColour,
            type,
            randInt + colorName + " " + ((ChessPieceType) type).name().toLowerCase());
      case PAWN:
        return new Piece(
            List.of(new PawnMovement(), new PawnTaking(), new PawnFirstMove()),
            pieceColour,
            type,
            randInt + colorName + " " + ((ChessPieceType) type).name().toLowerCase());
      case BISHOP:
        return new Piece(
            List.of(new DiagonalMovement()),
            pieceColour,
            type,
            randInt + colorName + " " + ((ChessPieceType) type).name().toLowerCase());
      case KNIGHT:
        return new Piece(
            List.of(new KnightMovement()),
            pieceColour,
            type,
            randInt + colorName + " " + ((ChessPieceType) type).name().toLowerCase());
      case ARCHBISHOP:
        return new Piece(
            List.of(new DiagonalMovement(), new KnightMovement()),
            pieceColour,
            type,
            randInt + colorName + " " + ((ChessPieceType) type).name().toLowerCase());
      case CHANCELLOR:
        return new Piece(
            List.of(new KnightMovement(), new VerticalMovement(), new HorizontalMovement()),
            pieceColour,
            type,
            randInt + colorName + " " + ((ChessPieceType) type).name().toLowerCase());
      default:
        throw new IllegalArgumentException();
    }
  }
}
