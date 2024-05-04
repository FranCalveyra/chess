package edu.austral.dissis.chess.rules;

import edu.austral.dissis.chess.engine.ChessGame;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.utils.move.ChessMove;
import java.util.List;

public class PieceValidMove implements PreMovementRule {
  @Override
  public boolean isValidRule(ChessMove move, ChessGame game) {
    Piece pieceToMove = game.getBoard().pieceAt(move.from());
    final List<ChessMove> playToExecute = pieceToMove.getPlay(move, game.getBoard());
    // No moves available
    return !playToExecute.isEmpty();
  }

  @Override
  public String getStringErrorRepresentation() {
    return "Not a valid move for this piece";
  }
}
