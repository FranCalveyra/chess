package edu.austral.dissis.chess.promoter;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.piece.PieceType;
import edu.austral.dissis.chess.rule.movement.PieceMovementRule;
import edu.austral.dissis.chess.utils.Position;

import java.awt.*;
import java.util.List;

public interface Promoter {
    void promote(Position position, PieceType type, Board context, Color team) ;
}
