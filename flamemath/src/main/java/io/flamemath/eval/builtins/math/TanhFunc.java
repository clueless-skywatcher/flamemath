package io.flamemath.eval.builtins.math;

import java.util.List;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.Compound;
import io.flamemath.expr.Expr;
import io.flamemath.expr.IntegerAtom;
import io.flamemath.expr.RealAtom;
import static io.flamemath.FlameUtils.toNumericAtom;
import io.flamemath.FlameUtils;

public class TanhFunc implements FlameFunction {

    @Override
    public String name() {
        return "Tanh";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 1) {
            throw new FlameArityException(name(), 1, args.size());
        }

        Expr arg = args.get(0);

        // Numeric → compute directly
        if (arg instanceof IntegerAtom i) {
            return toNumericAtom(Math.tanh(i.value()));
        }
        if (arg instanceof RealAtom r) {
            return toNumericAtom(Math.tanh(r.value()));
        }

        return new Compound(name(), args);
    }

    @Override
    public Expr numerify(List<Expr> args) throws Exception {
        return toNumericAtom(Math.tanh(FlameUtils.numericValue(args.get(0))));
    }
}
