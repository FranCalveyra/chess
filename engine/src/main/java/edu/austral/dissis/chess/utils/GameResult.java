package edu.austral.dissis.chess.utils;

import edu.austral.dissis.chess.engine.ChessGame;

public record GameResult(ChessGame game, ChessMoveResult message) {}
