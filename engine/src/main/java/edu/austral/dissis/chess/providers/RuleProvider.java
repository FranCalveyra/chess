package edu.austral.dissis.chess.providers;

import edu.austral.dissis.chess.rules.premovement.MoveNotIntoCheck;
import edu.austral.dissis.chess.rules.winconds.CheckMate;
import edu.austral.dissis.common.rules.premovement.validators.AndValidator;
import edu.austral.dissis.common.rules.premovement.validators.AvoidFriendlyFire;
import edu.austral.dissis.common.rules.premovement.validators.InsideBoardBounds;
import edu.austral.dissis.common.rules.premovement.validators.PieceAtPosition;
import edu.austral.dissis.common.rules.premovement.validators.PieceValidMove;
import edu.austral.dissis.common.rules.premovement.validators.PreMovementValidator;
import edu.austral.dissis.common.rules.premovement.validators.TakesPieceWhenPossible;
import edu.austral.dissis.common.rules.premovement.validators.TurnRule;
import edu.austral.dissis.common.rules.winconds.WinCondition;
import edu.austral.dissis.common.utils.enums.GameType;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class RuleProvider {
  public List<WinCondition> provideWinConditions(GameType type) {
    if (type == GameType.DEFAULT_CHESS) {
      return (List.of(new CheckMate(Color.WHITE), new CheckMate(Color.BLACK)));
    }
    return new ArrayList<>();
  }

  public PreMovementValidator providePreMovementValidator(GameType type) {
    PreMovementValidator bottomLeft =
        new AndValidator(new InsideBoardBounds(), new PieceAtPosition());
    PreMovementValidator bottomRight = new AndValidator(new PieceValidMove(), new TurnRule());
    PreMovementValidator right =
        new AndValidator(
            type == GameType.DEFAULT_CHECKERS
                ? new TakesPieceWhenPossible()
                : new MoveNotIntoCheck(),
            new AvoidFriendlyFire());
    PreMovementValidator left = new AndValidator(bottomLeft, bottomRight);
    return new AndValidator(left, right);
  }
}
