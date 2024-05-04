package edu.austral.dissis.chess.rules;

import edu.austral.dissis.chess.engine.ChessGame;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.utils.move.ChessMove;

public class AvoidFriendlyFire implements PreMovementRule {
  @Override
  public boolean isValidRule(ChessMove move, ChessGame game) {
    Piece pieceToMove = game.getBoard().pieceAt(move.from());
    Piece pieceAtDestination = game.getBoard().pieceAt(move.to());
    return pieceAtDestination == null
        || pieceToMove.getPieceColour() != pieceAtDestination.getPieceColour();
  }

  @Override
  public String getStringErrorRepresentation() {
    return "Friendly fire";
  }
}
