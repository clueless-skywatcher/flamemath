package io.flamemath.eval.builtins.math;

import java.util.List;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.Compound;
import io.flamemath.expr.Expr;
import io.flamemath.expr.IntegerAtom;
import io.flamemath.expr.RealAtom;

public class AbsFunc implements FlameFunction {
    @Override
    public String name() {
        return "Abs";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 1) {
            throw new FlameArityException(name(), 1, args.size());
        }

        if (args.get(0) instanceof IntegerAtom i) {
            long value = i.value();
            if (value < 0)
                return new IntegerAtom(-value);
            return i;
        }
        if (args.get(0) instanceof RealAtom r) {
            double value = r.value();
            if (value < 0)
                return new RealAtom(-value);
            return r;
        }

        return new Compound(name(), args);
    }

    @Override
    public Expr numerify(List<Expr> args) throws Exception {
        return new RealAtom(Math.abs(io.flamemath.FlameUtils.numericValue(args.get(0))));
    }
}
