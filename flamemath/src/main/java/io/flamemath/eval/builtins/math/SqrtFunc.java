package io.flamemath.eval.builtins.math;

import java.util.List;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.Compound;
import io.flamemath.expr.Expr;
import io.flamemath.expr.IntegerAtom;
import io.flamemath.expr.RationalAtom;
import io.flamemath.expr.RealAtom;

public class SqrtFunc implements FlameFunction {
    @Override
    public String name() {
        return "Sqrt";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 1) {
            throw new FlameArityException(name(), 1, args.size());
        }
        Expr arg = args.get(0);

        // Sqrt(n) → Pow(n, 1/2), let PowFunc handle perfect squares
        if (arg instanceof IntegerAtom || arg instanceof RationalAtom) {
            return evaluator.eval(
                    new Compound("Pow", List.of(arg, RationalAtom.of(1, 2))));
        }

        if (arg instanceof RealAtom r) {
            return new RealAtom(Math.sqrt(r.value()));
        }

        return new Compound(name(), args);
    }

    @Override
    public Expr numerify(List<Expr> args) throws Exception {
        return new RealAtom(Math.sqrt(io.flamemath.FlameUtils.numericValue(args.get(0))));
    }
}
