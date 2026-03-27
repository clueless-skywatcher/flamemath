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

public class ArcTan2Func implements FlameFunction {

    @Override
    public String name() {
        return "ArcTan2";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 2) {
            throw new FlameArityException(name(), 2, args.size());
        }

        Expr y = args.get(0);
        Expr x = args.get(1);

        // Both numeric → compute directly
        if (isNumeric(y) && isNumeric(x)) {
            double yv = numVal(y);
            double xv = numVal(x);
            if (yv == 0 && xv == 0) {
                throw new ArithmeticException("ArcTan2 undefined: both arguments are zero");
            }
            return toNumericAtom(Math.atan2(yv, xv));
        }

        return new Compound(name(), args);
    }

    private boolean isNumeric(Expr e) {
        return e instanceof IntegerAtom || e instanceof RealAtom;
    }

    private double numVal(Expr e) {
        if (e instanceof IntegerAtom i) return i.value().toDouble();
        if (e instanceof RealAtom r) return r.value();
        throw new IllegalArgumentException("Not numeric: " + e);
    }

    @Override
    public Expr numerify(List<Expr> args) throws Exception {
        return toNumericAtom(Math.atan2(
                FlameUtils.numericValue(args.get(0)),
                FlameUtils.numericValue(args.get(1))));
    }
}
