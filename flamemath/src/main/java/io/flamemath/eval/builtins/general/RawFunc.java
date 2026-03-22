package io.flamemath.eval.builtins.general;

import java.util.List;
import java.util.Set;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.Compound;
import io.flamemath.expr.Expr;
import io.flamemath.expr.Symbol;

public class RawFunc implements FlameFunction {

    @Override
    public String name() {
        return "Raw";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() < 1) {
            throw FlameArityException.atLeast(name(), 1, args.size());
        }
        if (!(args.get(0) instanceof Symbol s)) {
            throw new Exception("First argument must be a symbol");
        }
        return new Compound(s.name(), args.subList(1, args.size()));
    }

    @Override
    public Set<Integer> heldArgIndexes() {
        return Set.of(0);
    }
}
