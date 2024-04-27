package edu.austral.dissis.chess.ui;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.engine.ChessGame;
import edu.austral.dissis.chess.gui.*;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.utils.ChessMove;
import edu.austral.dissis.chess.utils.ChessMoveResult;
import edu.austral.dissis.chess.utils.ChessPosition;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static edu.austral.dissis.chess.utils.ChessMoveResult.*;
import static java.awt.Color.BLACK;
import static java.awt.Color.WHITE;

public class ChessGameEngine implements GameEngine {

    private final ChessGame game;
    private final Map<ChessMoveResult, MoveResult> moveResults;

    public ChessGameEngine(ChessGame game) {
        this.game = game;
        this.moveResults =
                Map.of(VALID_MOVE, updateGameState(game),
                        INVALID_MOVE, new InvalidMove("Invalid move"),
                        PIECE_TAKEN, updateGameState(game),
                        WHITE_WIN, new GameOver(getPlayerColor(WHITE)),
                        BLACK_WIN, new GameOver(getPlayerColor(BLACK))
                        );
    }



    @NotNull
    @Override
    public MoveResult applyMove(@NotNull Move move) {
        return moveResults.get(game.makeMove(new ChessMove(mapPos(move.component1()), mapPos(move.component2()))).message());
    }

    @NotNull
    @Override
    public InitialState init() {
        return new InitialState(new BoardSize(game.getBoard().getColumns(), game.getBoard().getRows()), getPiecesList(game),getPlayerColor(WHITE));
    }

    private static PlayerColor getPlayerColor(Color color) {
        return color == BLACK ? PlayerColor.BLACK : PlayerColor.WHITE;
    }
    private static Position getPiecePosition(Piece piece, Board board) {
        for(Map.Entry<ChessPosition, Piece> entry : board.getPiecesAndPositions().entrySet()) {
            if(entry.getValue().equals(piece)) {
                return new Position(entry.getKey().getRow()+1, entry.getKey().getColumn()+1); //From zero based (I think)
            }
        }
        return null;
    }

    private MoveResult updateGameState(ChessGame game) {
        return new NewGameState(getPiecesList(game), getPlayerColor(game.getCurrentTurn()));
    }

    private @NotNull List<ChessPiece> getPiecesList(ChessGame game) {
        return game.getBoard().getPiecesAndPositions()
                .values().stream().map(piece -> new ChessPiece(piece.getId(),
                        getPlayerColor(piece.getPieceColour()),
                        Objects.requireNonNull(getPiecePosition(piece, game.getBoard())),
                        piece.getType().toString().toLowerCase())).toList();
    }

    private ChessPosition mapPos(Position position) {
        return new ChessPosition(position.getRow()-1, position.getColumn()-1);
    }
}
