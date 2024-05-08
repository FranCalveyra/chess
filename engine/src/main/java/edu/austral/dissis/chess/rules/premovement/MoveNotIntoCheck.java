package edu.austral.dissis.chess.rules.premovement;

import edu.austral.dissis.chess.engine.ChessGame;
import edu.austral.dissis.chess.rules.winconds.Check;
import edu.austral.dissis.chess.utils.Pair;
import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.piece.Piece;
import edu.austral.dissis.common.rules.premovement.PreMovementRule;
import edu.austral.dissis.common.utils.move.GameMove;
import edu.austral.dissis.common.utils.result.PlayResult;
import edu.austral.dissis.common.utils.result.ValidPlay;
import java.util.List;

public class MoveNotIntoCheck implements PreMovementRule {
  @Override
  public boolean isValidRule(GameMove move, ChessGame game) {
    List<Check> checks = game.getCheckConditions();
    Piece piece = game.getBoard().pieceAt(move.from());
    Pair<Board, PlayResult> resultPair = new Pair<>(game.getBoard(), new ValidPlay());
    final List<GameMove> playToExecute = piece.getPlay(move, game.getBoard());
    for (GameMove moveToDo : playToExecute) {
      resultPair =
          game.getMoveExecutor()
              .executeMove(moveToDo.from(), moveToDo.to(), resultPair.first(), game.getPromoter());
    }
    Pair<Board, PlayResult> finalResultPair = resultPair;
    return checks.stream()
        .anyMatch(
            check ->
                check.getTeam() == game.getBoard().pieceAt(move.from()).getPieceColour()
                    && !check.isValidRule(finalResultPair.first()));
  }
}
