package edu.austral.dissis.checkers.providers;

import edu.austral.dissis.common.piece.Piece;
import edu.austral.dissis.common.piece.PieceType;
import java.awt.Color;
import java.util.Random;

public class CheckersPieceProvider {
  // TODO: IMPLEMENT
  public Piece provideCheckersPiece(Color team, PieceType type) {
    String colorName = team == Color.WHITE ? "white" : "black";
    int randInt = new Random().nextInt();
    // TODO
    return null;
  }
}
