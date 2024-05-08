package edu.austral.dissis.checkers.piece.movement.type;

import edu.austral.dissis.checkers.piece.movement.CheckersType;
import edu.austral.dissis.checkers.piece.movement.restrictions.PieceBetweenIsAnEnemy;
import edu.austral.dissis.chess.validators.AndRestrictionValidator;
import edu.austral.dissis.chess.validators.MovementRestrictionValidator;
import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.piece.Piece;
import edu.austral.dissis.common.piece.PieceType;
import edu.austral.dissis.common.piece.movement.restrictions.*;
import edu.austral.dissis.common.piece.movement.type.PieceMovement;
import edu.austral.dissis.common.utils.move.GameMove;

import java.awt.Color;

public class CheckersNormalMovement implements PieceMovement {
    @Override
    public boolean isValidMove(GameMove move, Board context) {
        Piece piece = context.pieceAt(move.from());
        final MovementRestrictionValidator validator = getCheckersMovementValidator(piece.getPieceColour(), piece.getType());
        return validator.isValidMove(move,context);
    }

    protected static MovementRestrictionValidator getCheckersMovementValidator(Color team, PieceType type) {
        MovementRestrictionValidator dx = new AndRestrictionValidator(new AbsColumnDistance(2));
        int colorBasedRowDistance = team == Color.RED ? -2 : 2;
        MovementRestriction rowRestriction = type == CheckersType.NORMAL ? new RowDistance(colorBasedRowDistance) : new AbsRowDistance(2);
        MovementRestrictionValidator dy = new AndRestrictionValidator(rowRestriction, dx, null);
        MovementRestrictionValidator enemyBetween = new AndRestrictionValidator(new PieceBetweenIsAnEnemy(), dy,null);
        return new AndRestrictionValidator(new ClearTile(), enemyBetween,null);

    }
}
