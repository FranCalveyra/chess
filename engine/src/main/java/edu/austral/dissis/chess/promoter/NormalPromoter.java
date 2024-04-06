package edu.austral.dissis.chess.promoter;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.piece.PieceType;
import edu.austral.dissis.chess.provider.PieceProvider;
import edu.austral.dissis.chess.utils.Position;

import java.awt.*;

public class NormalPromoter implements Promoter {

    @Override
    public void promote(Position position, PieceType type, Board context, Color team) {
        Piece piece = new PieceProvider().get(team, type);
        context.removePieceAt(position);
        context.addPieceAt(position,piece);
    }
}
