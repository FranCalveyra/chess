package edu.austral.dissis.chess.providers;

import edu.austral.dissis.chess.rules.AvoidFriendlyFire;
import edu.austral.dissis.chess.rules.CheckMate;
import edu.austral.dissis.chess.rules.InsideBoardBounds;
import edu.austral.dissis.chess.rules.MoveNotIntoCheck;
import edu.austral.dissis.chess.rules.PieceAtPosition;
import edu.austral.dissis.chess.rules.PieceValidMove;
import edu.austral.dissis.chess.rules.TurnRule;
import edu.austral.dissis.chess.rules.WinCondition;
import edu.austral.dissis.chess.utils.GameType;
import edu.austral.dissis.chess.validators.AndTreePreMovementValidator;
import edu.austral.dissis.chess.validators.PreMovementValidator;
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
    if (type != GameType.DEFAULT) {
      return null;
    }
    AndTreePreMovementValidator moveInside =
        new AndTreePreMovementValidator(new InsideBoardBounds(), null, null);
    AndTreePreMovementValidator pieceAtPos =
        new AndTreePreMovementValidator(new PieceAtPosition(), null, null);
    AndTreePreMovementValidator turn =
        new AndTreePreMovementValidator(new TurnRule(), pieceAtPos, null);

    AndTreePreMovementValidator moveAllowed =
        new AndTreePreMovementValidator(new PieceValidMove(), null, null);
    AndTreePreMovementValidator noFriendlyFire =
        new AndTreePreMovementValidator(new AvoidFriendlyFire(), null, null);
    AndTreePreMovementValidator notIntoCheck =
        new AndTreePreMovementValidator(new MoveNotIntoCheck(), null, null);

    AndTreePreMovementValidator bottomLeft =
        new AndTreePreMovementValidator(null, moveInside, turn);
    AndTreePreMovementValidator bottomRight =
        new AndTreePreMovementValidator(null, moveAllowed, noFriendlyFire);
    AndTreePreMovementValidator topLeft =
        new AndTreePreMovementValidator(null, bottomLeft, bottomRight);

    return new AndTreePreMovementValidator(null, topLeft, notIntoCheck);
  }
}
