package edu.austral.dissis.common.rules.premovement.rules;

import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.engine.BoardGame;
import edu.austral.dissis.common.piece.Piece;
import edu.austral.dissis.common.piece.movement.type.PieceMovement;
import edu.austral.dissis.common.piece.movement.type.TakingMovement;
import edu.austral.dissis.common.utils.move.BoardPosition;
import edu.austral.dissis.common.utils.move.GameMove;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static edu.austral.dissis.common.utils.AuxStaticMethods.*;

public class TakesPieceWhenPossible implements PreMovementRule {
  @Override
  public boolean isValidRule(GameMove move, BoardGame game) {
    // TODO: obligue the player to move ONLY that piece
    // If the piece has a TakingMove type movement, execute it.
    Map<BoardPosition, Piece> teamPieces = getPiecesByColor(game.getBoard(),game.getBoard().pieceAt(move.from()).getPieceColour());
    List<GameMove> allAttackingMoves = getTeamAttackingMoves(teamPieces, game.getBoard());
    if(allAttackingMoves.isEmpty()) {
      return true;
    };
    return currentPieceIsAttacking(move, game) && allAttackingMoves.getFirst().from().equals(move.from());
  }

  private List<GameMove> getTeamAttackingMoves(Map<BoardPosition, Piece> teamPieces, Board board) {
    List<GameMove> attackingMoves = new ArrayList<>();
    for(Map.Entry<BoardPosition, Piece> entry : teamPieces.entrySet()) {
      Piece piece = entry.getValue();
      PieceMovement takingMove = getTakingMove(piece);
      if(takingMove == null){
        continue;
      }
      attackingMoves.addAll(getAttackingMoves(entry.getKey(), takingMove, board));
    }
    return attackingMoves.stream().distinct().toList();
  }


  private boolean currentPieceIsAttacking(GameMove move, BoardGame game) {
    List<PieceMovement> pieceMovements = game.getBoard().pieceAt(move.from()).getMovements();
    if (pieceMovements.stream().noneMatch(movement -> movement instanceof TakingMovement)) {
      return true;
    }
    // Fetch all possible attacking movements
    Optional<PieceMovement> takingMove =
        pieceMovements.stream().filter(movement -> movement instanceof TakingMovement).findFirst();
    PieceMovement takingMovement = takingMove.orElse(null);

    if (takingMovement == null) {
      return true;
    }

    List<BoardPosition> possiblePositions =
        takingMovement.getPossiblePositions(move.from(), game.getBoard());
    // If there's any, check the intended movement effectively attacks
    if (possiblePositions.isEmpty()) {
      return true;
    }
    // TODO: obligue last moved piece to take
    return possiblePositions.contains(move.to());
  }
}
