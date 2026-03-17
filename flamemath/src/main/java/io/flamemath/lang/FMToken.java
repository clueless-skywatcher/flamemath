package io.flamemath.lang;

public record FMToken(FMTokenType type, String value, int line, int col) {
    @Override
    public String toString() {
        return type + "(" + value + ") @ " + line + ":" + col;
    }
}
