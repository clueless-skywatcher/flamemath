package io.flamemath.exceptions;

import java.util.Arrays;
import java.util.stream.Collectors;

public class FlameArityException extends Exception {
    public FlameArityException(String message) {
        super(message);
    }

    public FlameArityException(String functionName, int expected, int actual) {
        super(functionName + " expects " + expected + " argument" + (expected != 1 ? "s" : "")
                + ", got " + actual);
    }

    public FlameArityException(String functionName, int[] expected, int actual) {
        super(functionName + " expects "
                + Arrays.stream(expected).mapToObj(String::valueOf).collect(Collectors.joining(" or "))
                + " arguments, got " + actual);
    }

    public static FlameArityException atLeast(String functionName, int minimum, int actual) {
        return new FlameArityException(functionName + " expects at least " + minimum
                + " argument" + (minimum != 1 ? "s" : "") + ", got " + actual);
    }

    public static FlameArityException atMost(String functionName, int maximum, int actual) {
        return new FlameArityException(functionName + " expects at most " + maximum
                + " argument" + (maximum != 1 ? "s" : "") + ", got " + actual);
    }
}
