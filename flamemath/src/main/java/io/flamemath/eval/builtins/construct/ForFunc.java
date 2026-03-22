package io.flamemath.eval.builtins.construct;

import java.util.List;
import java.util.Set;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.eval.FlameVironment;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.Expr;
import io.flamemath.expr.ListExpr;
import io.flamemath.expr.NullExpr;
import io.flamemath.expr.Symbol;

public class ForFunc implements FlameFunction {

    @Override
    public String name() {
        return "For";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 3) {
            throw new FlameArityException(name(), 3, args.size());
        }

        if (!(args.get(0) instanceof Symbol s)) {
            throw new IllegalArgumentException("First argument to For must be a symbol");
        }
        if (!(args.get(1) instanceof ListExpr l)) {
            throw new IllegalArgumentException("Second argument to For must be a list");
        }

        // For(var, list, body)
        FlameVironment oldEnv = evaluator.getEnv();
        evaluator.setEnv(new FlameVironment(oldEnv));

        try {
            for (int i = 0; i < l.exprs().size(); i++) {
                Expr value = l.exprs().get(i);
                evaluator.getEnv().set(s, value);
                evaluator.eval(args.get(2));
            }
        } finally {
            evaluator.setEnv(oldEnv);
        }

        return NullExpr.INSTANCE;
    }

    @Override
    public Set<Integer> heldArgIndexes() {
        return Set.of(0, 2);
    }
}
