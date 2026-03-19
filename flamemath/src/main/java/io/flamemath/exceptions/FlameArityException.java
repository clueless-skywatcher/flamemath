package io.flamemath.exceptions;

public class FlameArityException extends Exception {
    public FlameArityException(String functionName, int expected, int actual) {
        super(functionName + " expects " + expected + " argument" + (expected != 1 ? "s" : "")
                + ", got " + actual);
    }
}
