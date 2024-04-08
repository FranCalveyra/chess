package edu.austral.dissis.chess.piece.movement;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.utils.Position;

public class HorizontalMovement implements PieceMovement{
    @Override
    public boolean isValidMove(Position oldPos, Position newPos, Board context) {
        return oldPos.getColumn() != newPos.getColumn() && oldPos.getRow() == newPos.getRow() && noPieceBetween(oldPos,newPos,context);
    }

    @Override
    public boolean noPieceBetween(Position oldPos, Position newPos, Board context) {
        int fromColumn = Math.min(oldPos.getRow(), newPos.getRow());
        int toColumn = Math.max(oldPos.getRow(), newPos.getRow());
        for (int j = fromColumn; j <=toColumn ; j++) {
            Position currentTile = new Position(oldPos.getRow(),j);
            if(context.pieceAt(currentTile) == null || context.pieceAt(currentTile).getPieceColour() != context.getCurrentTurn()) return true;
        }
        return false;
    }
}
