package io.flamemath.eval.builtins.math;

import static io.flamemath.FlameUtils.numericValue;

import java.util.List;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.Compound;
import io.flamemath.expr.Expr;
import io.flamemath.expr.IntegerAtom;
import io.flamemath.expr.RealAtom;
import io.flamemath.expr.Symbol;

public class ExpFunc implements FlameFunction {
    @Override
    public String name() {
        return "Exp";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 1) {
            throw new FlameArityException(name(), 1, args.size());
        }

        Expr arg = args.get(0);

        // Exp(x) → Pow(E, x)
        return evaluator.eval(new Compound("Pow", List.of(new Symbol("E"), arg)));
    }

    @Override
    public Expr numerify(List<Expr> args) throws Exception {
        return new RealAtom(Math.exp(numericValue(args.get(0))));
    }
}
