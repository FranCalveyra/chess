package edu.austral.dissis.chess.rule.movement;

import edu.austral.dissis.chess.utils.Position;

public interface PieceMovementRule {
  // If I make a PieceMovementRule dependent on the context, O(ruleCheck) can be enormous.
  // Deficient.

  boolean isValidMove(Position oldPos, Position newPos);
}
