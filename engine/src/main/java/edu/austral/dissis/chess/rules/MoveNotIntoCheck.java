package edu.austral.dissis.chess.rules;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.engine.ChessGame;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.utils.Pair;
import edu.austral.dissis.chess.utils.move.ChessMove;
import edu.austral.dissis.chess.utils.result.ChessMoveResult;
import edu.austral.dissis.chess.utils.result.ValidMove;
import java.util.List;

public class MoveNotIntoCheck implements PreMovementRule {
  @Override
  public boolean isValidRule(ChessMove move, ChessGame game) {
    List<Check> checks = game.getCheckConditions();
    Piece piece = game.getBoard().pieceAt(move.from());
    Pair<Board, ChessMoveResult> resultPair = new Pair<>(game.getBoard(), new ValidMove());
    final List<ChessMove> playToExecute = piece.getPlay(move, game.getBoard());
    for (ChessMove moveToDo : playToExecute) {
      resultPair =
          game.getMoveExecutor()
              .executeMove(moveToDo.from(), moveToDo.to(), resultPair.first(), game.getPromoter());
    }
    Pair<Board, ChessMoveResult> finalResultPair = resultPair;
    return checks.stream()
        .anyMatch(
            check ->
                check.team() == game.getBoard().pieceAt(move.from()).getPieceColour()
                    && !check.isValidRule(finalResultPair.first()));
  }

  @Override
  public String getStringErrorRepresentation() {
    return "Move leaves king in check";
  }
}
