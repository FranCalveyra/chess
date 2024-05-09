package edu.austral.dissis.checkers.providers;

import edu.austral.dissis.checkers.piece.movement.CheckersType;
import edu.austral.dissis.checkers.piece.movement.type.CheckersMovement;
import edu.austral.dissis.checkers.piece.movement.type.CheckersTakingMovement;
import edu.austral.dissis.chess.piece.movement.type.ChessPieceType;
import edu.austral.dissis.common.piece.Piece;
import edu.austral.dissis.common.piece.PieceType;
import java.awt.Color;
import java.util.List;
import java.util.Random;

import static edu.austral.dissis.checkers.piece.movement.CheckersType.KING;
import static edu.austral.dissis.checkers.piece.movement.CheckersType.MAN;
import static edu.austral.dissis.chess.providers.ChessPieceProvider.getId;

public class CheckersPieceProvider {
  public Piece provideCheckersPiece(Color team, PieceType type) {
    String colorName = team == Color.RED ? "white" : "black";
    int randInt = new Random().nextInt();
    switch(type){
      case KING:
        return new Piece(List.of(new CheckersTakingMovement(), new CheckersMovement()), team, type, getId(ChessPieceType.QUEEN,randInt,colorName));
      case MAN:
        return new Piece(List.of(new CheckersTakingMovement(), new CheckersMovement()), team, type, getId(ChessPieceType.PAWN,randInt,colorName));
        default:
            throw new IllegalStateException("Unexpected value: " + type);
    }
  }
}
