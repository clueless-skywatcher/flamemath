package io.flamemath;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import io.flamemath.eval.FlameValuator;
import io.flamemath.expr.Expr;
import io.flamemath.lang.parser.FlameParser;

public class FlameMath {
    public static final String VERSION = "0.2.0";

    public static void main(String[] args) throws IOException {
        System.out.println("FlameMath " + VERSION);
        System.out.println("Type an expression, or \"exit\" to quit.\n");

        var reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        System.out.print("Flame> ");
        FlameValuator eval = new FlameValuator();
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
}