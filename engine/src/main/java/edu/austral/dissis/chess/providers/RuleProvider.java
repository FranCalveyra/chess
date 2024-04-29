package edu.austral.dissis.chess.providers;

import edu.austral.dissis.chess.rules.*;
import edu.austral.dissis.chess.utils.GameType;
import edu.austral.dissis.chess.validators.PreMovementValidator;
import edu.austral.dissis.chess.validators.TreePreMovementValidator;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class RuleProvider {
  public List<WinCondition> provideWinConditions(GameType type) {
    if (type == GameType.DEFAULT) {
      return (List.of(new CheckMate(Color.WHITE), new CheckMate(Color.BLACK)));
    }
    return new ArrayList<>();
  }
  public PreMovementValidator providePreMovementValidator(GameType type) {
    if(type != GameType.DEFAULT) {
      return null;
    }
    TreePreMovementValidator moveInside = new TreePreMovementValidator(new InsideBoardBounds(), null, null);
    TreePreMovementValidator pieceAtPos = new TreePreMovementValidator(new PieceAtPosition(), null,null);
    TreePreMovementValidator turn = new TreePreMovementValidator(new TurnRule(), pieceAtPos, null);

    TreePreMovementValidator moveAllowed = new TreePreMovementValidator(new PieceValidMove(), null, null);
    TreePreMovementValidator noFriendlyFire = new TreePreMovementValidator(new AvoidFriendlyFire(), null, null);
    TreePreMovementValidator notIntoCheck = new TreePreMovementValidator(new MoveNotIntoCheck(), null, null);

    TreePreMovementValidator bottomLeft = new TreePreMovementValidator(null, moveInside, turn);
    TreePreMovementValidator bottomRight = new TreePreMovementValidator(null, moveAllowed, noFriendlyFire);
    TreePreMovementValidator topLeft = new TreePreMovementValidator(null, bottomLeft, bottomRight);

    return new TreePreMovementValidator(null, topLeft, notIntoCheck);
  }
}
