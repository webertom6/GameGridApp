package be.weber.sokoban.code.gui;

public class SokobanError extends Exception {
    public SokobanError() {
    }

    public SokobanError(String message) {
        super(message);
    }
}