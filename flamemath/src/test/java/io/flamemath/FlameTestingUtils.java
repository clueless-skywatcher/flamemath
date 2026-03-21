package io.flamemath;

import io.flamemath.eval.FlameValuator;
import io.flamemath.expr.Expr;
import io.flamemath.lang.parser.FlameParser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class FlameTestingUtils {
    private FlameValuator eval = new FlameValuator();
    private ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    private PrintStream stream = new PrintStream(outStream);
    private PrintStream oldStream = System.out;

    public Expr execute(String expr) throws Exception {
        return eval.eval(new FlameParser(expr).parse());
    }

    public void setStream() {
        System.setOut(stream);
    }

    public void unsetStream() {
        System.setOut(oldStream);
    }

    public void assertExec(String expected, String actual) throws Exception {
        Expr expectedResult = execute(expected);
        Expr actualResult = execute(actual);
        assertEquals(expectedResult, actualResult,
                "Expected: " + expected + " → " + expectedResult + "\n" +
                "Actual:   " + actual + " → " + actualResult);
    }

    public void assertPrint(String actual) throws Exception {
        assertEquals(actual, outStream.toString());
    }
}