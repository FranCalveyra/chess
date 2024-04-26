package edu.austral.dissis.chess.engine.updated.runners;

import edu.austral.dissis.chess.engine.Board;
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
        return getValidPositions(testBoard,fromPosition).contains(toPosition) ? Validity.VALID : Validity.INVALID;
    }

    private List<TestPosition> getValidPositions(@NotNull TestBoard testBoard, @NotNull TestPosition fromPosition) {
        Board board = mapBoard(testBoard);
        List<Position> moveSet = board.pieceAt(mapPos(fromPosition)).getMoveSet(mapPos(fromPosition), board);
        return new ArrayList<>(moveSet
                .stream().map(position -> new TestPosition(position.getRow(), position.getColumn())).toList());
    }
}
