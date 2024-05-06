package edu.austral.dissis.chess.providers;

import edu.austral.dissis.chess.rules.premovement.AvoidFriendlyFire;
import edu.austral.dissis.chess.rules.premovement.InsideBoardBounds;
import edu.austral.dissis.chess.rules.premovement.MoveNotIntoCheck;
import edu.austral.dissis.chess.rules.premovement.PieceAtPosition;
import edu.austral.dissis.chess.rules.premovement.PieceValidMove;
import edu.austral.dissis.chess.rules.premovement.TurnRule;
import edu.austral.dissis.chess.rules.winconds.CheckMate;
import edu.austral.dissis.chess.rules.winconds.WinCondition;
import edu.austral.dissis.chess.utils.type.GameType;
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
        new AndTreePreMovementValidator(new InsideBoardBounds());
    AndTreePreMovementValidator pieceAt =
        new AndTreePreMovementValidator(new PieceAtPosition(), moveInside, null);
    AndTreePreMovementValidator moveAllowed = new AndTreePreMovementValidator(new PieceValidMove());
    AndTreePreMovementValidator noFriendlyFire =
        new AndTreePreMovementValidator(
            new AvoidFriendlyFire(), moveAllowed, new AndTreePreMovementValidator(new TurnRule()));
    return new AndTreePreMovementValidator(new MoveNotIntoCheck(), pieceAt, noFriendlyFire);
  }
}
