package io.flamemath.exceptions;

import java.util.Arrays;
import java.util.stream.Collectors;

public class FlameArityException extends Exception {
    public FlameArityException(String functionName, int expected, int actual) {
        super(functionName + " expects " + expected + " argument" + (expected != 1 ? "s" : "")
                + ", got " + actual);
    }

    public FlameArityException(String functionName, int[] expected, int actual) {
        super(functionName + " expects "
                + Arrays.stream(expected).mapToObj(String::valueOf).collect(Collectors.joining(" or "))
                + " arguments, got " + actual);
    }
}
