package edu.austral.dissis.common.rules.premovement.rules;

import edu.austral.dissis.common.engine.BoardGame;
import edu.austral.dissis.common.piece.Piece;
import edu.austral.dissis.common.piece.movement.type.PieceMovement;
import edu.austral.dissis.common.piece.movement.type.TakingMovement;
import edu.austral.dissis.common.utils.move.BoardPosition;
import edu.austral.dissis.common.utils.move.GameMove;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static edu.austral.dissis.common.utils.AuxStaticMethods.getPiecesByColor;

public class TakesPieceWhenPossible implements PreMovementRule {
  @Override
  public boolean isValidRule(GameMove move, BoardGame game) {
    //TODO: obligue the player to move ONLY that piece
    //If the piece has a TakingMove type movement, execute it.
    return currentPieceIsAttacking(move, game);
  }

  private boolean currentPieceIsAttacking(GameMove move, BoardGame game) {
    List<PieceMovement> pieceMovements = game.getBoard().pieceAt(move.from()).getMovements();
    if (pieceMovements.stream().noneMatch(movement -> movement instanceof TakingMovement)) {
      return true;
    }
    // Fetch all possible attacking movements
    Optional<PieceMovement> takingMove =
            pieceMovements.stream()
                    .filter(movement -> movement instanceof TakingMovement)
                    .findFirst();
    PieceMovement takingMovement = takingMove.orElse(null);

    if(takingMovement == null) {
      return true;
    }

    List<BoardPosition> possiblePositions =
            takingMovement.getPossiblePositions(move.from(), game.getBoard());
    // If there's any, check the intended movement effectively attacks
    if (possiblePositions.isEmpty()) {
      return true;
    }
    //TODO: obligue last moved piece to take
    return possiblePositions.contains(move.to());
  }
}
