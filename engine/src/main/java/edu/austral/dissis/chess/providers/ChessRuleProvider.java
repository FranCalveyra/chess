package edu.austral.dissis.chess.providers;

import edu.austral.dissis.chess.rules.premovement.MoveNotIntoCheck;
import edu.austral.dissis.chess.rules.winconds.CheckMate;
import edu.austral.dissis.chess.utils.enums.GameType;
import edu.austral.dissis.common.rules.premovement.rules.*;
import edu.austral.dissis.common.rules.premovement.validators.AndTreePreMovementValidator;
import edu.austral.dissis.common.rules.premovement.validators.PreMovementValidator;
import edu.austral.dissis.common.rules.winconds.WinCondition;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class ChessRuleProvider {
  public List<WinCondition> provideWinConditions(GameType type) {
    if (type == GameType.DEFAULT_CHESS) {
      return (List.of(new CheckMate(Color.WHITE), new CheckMate(Color.BLACK)));
    }
    return new ArrayList<>();
  }

  public PreMovementValidator providePreMovementValidator(GameType type) {
    AndTreePreMovementValidator moveInside =
        new AndTreePreMovementValidator(new InsideBoardBounds());
    AndTreePreMovementValidator pieceAt =
        new AndTreePreMovementValidator(new PieceAtPosition(), moveInside, null);
    AndTreePreMovementValidator moveAllowed = new AndTreePreMovementValidator(new PieceValidMove());
    AndTreePreMovementValidator noFriendlyFire =
        new AndTreePreMovementValidator(
            new AvoidFriendlyFire(), moveAllowed, new AndTreePreMovementValidator(new TurnRule()));
    return new AndTreePreMovementValidator(
        type == GameType.DEFAULT_CHECKERS ? new TakesPieceWhenPossible() : new MoveNotIntoCheck(), pieceAt, noFriendlyFire);
  }
}
