package io.flamemath.eval.builtins.system;

import java.util.List;
import java.util.StringJoiner;

import io.flamemath.ExprPrinter;
import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.expr.Expr;
import io.flamemath.expr.NullExpr;

public class PrintLnFunc implements FlameFunction {

    @Override
    public String name() {
        return "PrintLn";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.isEmpty()) {
            System.out.println();
            return NullExpr.INSTANCE;
        }
        StringJoiner joiner = new StringJoiner(" ");
        for (var arg: args) {
            String repr = ExprPrinter.print(
                evaluator.eval(arg)
            );
            if (repr.startsWith("\"") && repr.endsWith("\"")) {
                repr = repr.substring(1, repr.length() - 1);
            }
            joiner.add(repr);
        }
        System.out.println(joiner.toString());
        return NullExpr.INSTANCE;
    }
    
}
