package edu.austral.dissis.chess.engine.updated.runners;


import edu.austral.dissis.chess.engine.ChessGame;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.piece.PieceType;
import edu.austral.dissis.chess.test.*;
import edu.austral.dissis.chess.test.game.*;
import edu.austral.dissis.chess.utils.GameResult;
import edu.austral.dissis.chess.utils.Position;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static edu.austral.dissis.chess.engine.ChessGame.createChessGame;
import static edu.austral.dissis.chess.engine.updated.utils.GameAdapter.mapBoard;
import static edu.austral.dissis.chess.engine.updated.utils.GameAdapter.mapPosition;

public class ChessTestGameRunner implements TestGameRunner {

    private final ChessGame game;
    public ChessTestGameRunner(ChessGame game){
        this.game = game;
    }

    @NotNull
    @Override
    public TestMoveResult executeMove(@NotNull TestPosition from, @NotNull TestPosition to) {
        GameResult gameResult = game.makeMove(mapPosition(from), mapPosition(to));
        return switch (gameResult.getMessage()){
            case INVALID_MOVE -> new TestMoveFailure(mapTestBoard(game));
            case WHITE_WIN -> new BlackCheckMate(mapTestBoard(game));
            case BLACK_WIN -> new WhiteCheckMate(mapTestBoard(game));
            case VALID_MOVE, PIECE_TAKEN -> new TestMoveSuccess(this);
        };
    }

    @NotNull
    @Override
    public TestBoard getBoard() {
        return mapTestBoard(game);
    }

    @NotNull
    @Override
    public TestGameRunner withBoard(@NotNull TestBoard testBoard) {
        return new ChessTestGameRunner(createChessGame(mapBoard(testBoard), game.getWinConditions(), game.getCheckConditions(),game.getPromoter(), game.getSelector(), game.getCurrentTurn(), game.getTurnNumber()));
    }

    private TestBoard mapTestBoard(ChessGame game){
        return new TestBoard(new TestSize(game.getBoard().getRows(), game.getBoard().getColumns()),getTestMap(game.getBoard().getPiecesAndPositions()));
    }

    private Map<TestPosition, TestPiece> getTestMap(Map<Position, Piece> pieceMap){
        Map<TestPosition, TestPiece> testMap = new HashMap<>();
        pieceMap.forEach((currentPosition, value) -> testMap.put(TestPosition.Companion.fromZeroBased(currentPosition.getRow(), currentPosition.getColumn()), mapPiece(value)));
        return testMap;
    }

    private TestPiece mapPiece(Piece piece){
        char color = piece.getPieceColour() == Color.BLACK ? TestPieceSymbols.BLACK : TestPieceSymbols.WHITE;
        return new TestPiece(mapPieceType(piece.getType()),color);
    }
    private char mapPieceType(PieceType pieceType){
        return switch (pieceType){
            case KNIGHT -> TestPieceSymbols.KNIGHT;
            case BISHOP -> TestPieceSymbols.BISHOP;
            case ROOK -> TestPieceSymbols.ROOK;
            case QUEEN -> TestPieceSymbols.QUEEN;
            case KING -> TestPieceSymbols.KING;
            case PAWN -> TestPieceSymbols.PAWN;
        };
    }

}
