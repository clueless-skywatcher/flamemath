package io.flamemath;

import io.flamemath.eval.FlameValuator;
import io.flamemath.expr.Expr;
import io.flamemath.lang.parser.FlameParser;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FlameTestingUtils {
    private FlameValuator eval = new FlameValuator();

    public Expr execute(String expr) throws Exception {
        return eval.eval(new FlameParser(expr).parse());
    }

    public void assertExec(String expected, String actual) throws Exception {
        Expr expectedResult = execute(expected);
        Expr actualResult = execute(actual);
        assertEquals(expectedResult, actualResult,
                "Expected: " + expected + " → " + expectedResult + "\n" +
                "Actual:   " + actual + " → " + actualResult);
    }
}