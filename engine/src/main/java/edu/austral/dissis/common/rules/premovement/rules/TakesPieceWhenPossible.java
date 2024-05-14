package edu.austral.dissis.common.rules.premovement.rules;

import edu.austral.dissis.common.engine.BoardGame;
import edu.austral.dissis.common.piece.movement.type.PieceMovement;
import edu.austral.dissis.common.piece.movement.type.TakingMove;
import edu.austral.dissis.common.utils.move.BoardPosition;
import edu.austral.dissis.common.utils.move.GameMove;

import java.util.List;
import java.util.Objects;

public class TakesPieceWhenPossible implements PreMovementRule {
  @Override
  public boolean isValidRule(GameMove move, BoardGame game) {
    //TODO: obligue the player to move ONLY that piece
    //If the piece has a TakingMove type movement, execute it.
    List<PieceMovement> pieceMovements = game.getBoard().pieceAt(move.from()).getMovements();
    if (pieceMovements.stream().noneMatch(movement -> movement instanceof TakingMove)) {
      return true;
    }
    // Fetch all possible attacking movements
    PieceMovement takingMove =
        pieceMovements.stream()
            .filter(movement -> movement instanceof TakingMove)
            .findFirst()
            .get();
    List<BoardPosition> possiblePositions =
        takingMove.getPossiblePositions(move.from(), game.getBoard());
    // If there's any, check the intended movement effectively attacks
    if (possiblePositions.isEmpty()) {
      return true;
    }
    return possiblePositions.contains(move.to());
  }
}
