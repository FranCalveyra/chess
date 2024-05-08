package edu.austral.dissis.chess.providers;

import edu.austral.dissis.chess.rules.premovement.MoveNotIntoCheck;
import edu.austral.dissis.chess.rules.winconds.CheckMate;
import edu.austral.dissis.chess.utils.enums.GameType;
import edu.austral.dissis.common.rules.premovement.rules.AvoidFriendlyFire;
import edu.austral.dissis.common.rules.premovement.rules.InsideBoardBounds;
import edu.austral.dissis.common.rules.premovement.rules.PieceAtPosition;
import edu.austral.dissis.common.rules.premovement.rules.PieceValidMove;
import edu.austral.dissis.common.rules.premovement.rules.TurnRule;
import edu.austral.dissis.common.rules.premovement.validators.AndTreePreMovementValidator;
import edu.austral.dissis.common.rules.premovement.validators.PreMovementValidator;
import edu.austral.dissis.common.rules.winconds.WinCondition;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class ChessRuleProvider {
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
