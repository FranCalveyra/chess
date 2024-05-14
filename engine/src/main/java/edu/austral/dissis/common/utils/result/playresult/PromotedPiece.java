package edu.austral.dissis.common.utils.result.playresult;

public class PromotedPiece implements PlayResult{
    @Override
    public String getMessage() {
        return "Piece has been promoted";
    }

    @Override
    public String getType() {
        return "PromotedPiece";
    }
}
