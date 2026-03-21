package io.flamemath;

import io.flamemath.eval.FlameValuator;
import io.flamemath.expr.Expr;
import io.flamemath.lang.parser.FlameParser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class FlameStdlibTestUtils {
    private FlameValuator eval = new FlameValuator();

    public FlameStdlibTestUtils() {
        try {
            loadStdlib();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load stdlib", e);
        }
    }

    private void loadStdlib() throws Exception {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        String loadOrder;
        try (InputStream is = cl.getResourceAsStream("load-order.txt")) {
            loadOrder = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }
        for (String fileName : loadOrder.lines().toList()) {
            fileName = fileName.strip();
            if (fileName.isEmpty()) continue;
            try (InputStream is = cl.getResourceAsStream(fileName)) {
                String content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                eval.eval(new FlameParser(content).parseAll());
            }
        }
    }

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
