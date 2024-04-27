package edu.austral.dissis.chess.providers;

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
import java.util.Random;

public class PieceProvider {

  public Piece provide(Color pieceColour, PieceType type) {
    PieceMovement castling = new Castling();
    String colorName = pieceColour == Color.WHITE ? "white" : "black";
    int randInt = new Random().nextInt(100000);
    return switch (type) {
      case KING -> new Piece(List.of(new KingMovement(), castling), pieceColour, type, randInt + colorName + " " + type.name().toLowerCase());
      case ROOK -> new Piece(List.of(new RookMovement()), pieceColour, type, randInt + colorName + " " + type.name().toLowerCase());
      case QUEEN -> new Piece(List.of(new QueenMovement()), pieceColour, type, randInt + colorName + " " + type.name().toLowerCase());
      case PAWN ->
          new Piece(
              List.of(new PawnMovement(), new PawnTaking(), new PawnFirstMove()),
              pieceColour,
              type, randInt + colorName + " " + type.name().toLowerCase());
      case BISHOP -> new Piece(List.of(new DiagonalMovement()), pieceColour, type, randInt + colorName + " " + type.name().toLowerCase());
      case KNIGHT -> new Piece(List.of(new KnightMovement()), pieceColour, type, randInt + colorName + " " + type.name().toLowerCase());
    };
  }
}
