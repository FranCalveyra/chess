package edu.austral.dissis.chess.engine.updated.runners;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.test.TestBoard;
import edu.austral.dissis.chess.test.TestPosition;
import edu.austral.dissis.chess.test.Validity;
import edu.austral.dissis.chess.test.move.TestMoveRunner;
import edu.austral.dissis.chess.utils.Position;

import org.jetbrains.annotations.NotNull;


import java.util.ArrayList;
import java.util.List;


import static edu.austral.dissis.chess.engine.updated.utils.GameAdapter.mapBoard;
import static edu.austral.dissis.chess.engine.updated.utils.GameAdapter.mapPos;

public class ChessTestMoveRunner implements TestMoveRunner {
    @NotNull
    @Override
    public Validity executeMove(@NotNull TestBoard testBoard, @NotNull TestPosition fromPosition, @NotNull TestPosition toPosition) {
        boolean outOfBounds = checkOutOfBounds(testBoard, fromPosition) || checkOutOfBounds(testBoard, toPosition);
        boolean containCondition = getValidPositions(testBoard,fromPosition).contains(toPosition);
        return containCondition && outOfBounds? Validity.VALID : Validity.INVALID;
    }

    private List<TestPosition> getValidPositions(@NotNull TestBoard testBoard, @NotNull TestPosition fromPosition) {
        Board board = mapBoard(testBoard);
        Piece piece = board.pieceAt(mapPos(fromPosition));
        if(piece == null) return new ArrayList<>();
        List<Position> moveSet = piece.getMoveSet(mapPos(fromPosition), board);
        return new ArrayList<>(moveSet
                .stream().map(position -> TestPosition.Companion.fromZeroBased(position.getRow(), position.getColumn())).toList());
    }
    private boolean checkOutOfBounds(TestBoard testBoard, TestPosition position){
        Board board = mapBoard(testBoard);
        Position piecePos = mapPos(position);
        int i = piecePos.getRow();
        int j = piecePos.getColumn();
        return i>=board.getRows() || i<0 || j>=board.getColumns() || j<0;
    }
}
