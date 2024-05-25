package edu.austral.dissis.online.utils;

import edu.austral.dissis.chess.gui.Move;
import java.io.Serializable;

public record MovePayload(String id, Move move) implements Serializable {}
