package io.flamemath;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import io.flamemath.eval.FlameValuator;
import io.flamemath.expr.Expr;
import io.flamemath.lang.parser.FlameParser;

public class FlameMath {
    public static final String VERSION = "0.3.0";

    public static void main(String[] args) throws Exception {
        FlameValuator eval = new FlameValuator();
        loadStdlib(eval);

        System.out.println("FlameMath " + VERSION);
        System.out.println("Type an expression, or \"exit\" to quit.\n");

        var reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        System.out.print("Flame> ");
        while ((line = reader.readLine()) != null) {
            line = line.strip();
            if (line.isEmpty()) {
                System.out.print("Flame> ");
                continue;
            }

            try {
                Expr result = eval.eval(new FlameParser(line).parse());
                String resultString = ExprPrinter.print(result);
                if (!resultString.equals("Null")) {
                    System.out.println(resultString);
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            System.out.print("\nFlame> ");
        }
    }

    private static void loadStdlib(FlameValuator eval) throws Exception {
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
}