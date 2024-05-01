package edu.austral.dissis.chess.validators;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.engine.ChessGame;
import edu.austral.dissis.chess.piece.movement.restrictions.MovementRestriction;
import edu.austral.dissis.chess.rules.PreMovementRule;
import edu.austral.dissis.chess.utils.ChessMove;
import edu.austral.dissis.chess.utils.ChessMoveResult;

import static edu.austral.dissis.chess.utils.ChessMoveResult.INVALID_MOVE;
import static edu.austral.dissis.chess.utils.ChessMoveResult.VALID_MOVE;

public class AndRestrictionValidator implements MovementRestrictionValidator{
    private final MovementRestrictionValidator left;
    private final MovementRestrictionValidator right;
    private final MovementRestriction rule;

    public AndRestrictionValidator(
            MovementRestriction rule,
            MovementRestrictionValidator leftValidator,
            MovementRestrictionValidator rightValidator) {
        this.rule = rule;
        this.left = leftValidator;
        this.right = rightValidator;
    }
    @Override
    public boolean isValidMove(ChessMove move, Board context) {
        if (isLeaf()){
            return rule.isValidRule(move, context);
        }
        return getValidity(move, context);
    }


    private boolean isLeaf() {
        return left == null && right == null;
    }

    private boolean noRule() {
        return rule == null;
    }

    private boolean noLeftChild() {
        return left == null;
    }

    private boolean noRightChild() {
        return right == null;
    }

    private boolean getValidity(ChessMove move, Board board) {
        if (noRule()) {
            return validityWithoutRule(move, board);
        }
        return validityWithRule(move, board);
    }


    private boolean validityWithRule(ChessMove move, Board board) {
        if (noLeftChild() && !noRightChild()) {
            return rule.isValidRule(move, board) && right.isValidMove(move, board);
        } else if (!noLeftChild() && noRightChild()) {
            return left.isValidMove(move, board) && rule.isValidRule(move, board);
        } else if (isLeaf()) {
            return rule.isValidRule(move, board);
        }
        return left.isValidMove(move, board)
                && right.isValidMove(move, board)
                && rule.isValidRule(move, board);
    }

    private boolean validityWithoutRule(ChessMove move, Board board) {
        if (noLeftChild() && !noRightChild()) {
            return right.isValidMove(move, board);
        }
        if (noRightChild() && !noLeftChild()) {
            return left.isValidMove(move, board);
        }
        if (isLeaf()) {
            return false;
        }
        return left.isValidMove(move, board)
                && right.isValidMove(move, board);
    }


}
