package io.flamemath;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import io.flamemath.eval.FlameValuator;
import io.flamemath.expr.Expr;
import io.flamemath.lang.parser.FlameParser;

public class FlameMath {
    public static void main(String[] args) throws IOException {
        System.out.println("FlameMath 0.1.0");
        System.out.println("Type an expression, or \"exit\" to quit.\n");

        var reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        System.out.print("fm> ");
        FlameValuator eval = new FlameValuator();
        while ((line = reader.readLine()) != null) {
            line = line.strip();
            if (line.isEmpty()) {
                System.out.print("fm> ");
                continue;
            }

            try {
                Expr result = eval.eval(new FlameParser(line).parse());
                System.out.println(ExprPrinter.print(result));
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            System.out.print("\nfm> ");
        }
    }
}