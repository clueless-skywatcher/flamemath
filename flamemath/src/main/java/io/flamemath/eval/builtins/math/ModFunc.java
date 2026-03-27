package io.flamemath.eval.builtins.math;

import static io.flamemath.FlameUtils.*;

import java.util.List;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.Compound;
import io.flamemath.expr.Expr;
import io.flamemath.expr.IntegerAtom;
import io.flamemath.expr.RationalAtom;
import io.flamemath.expr.RealAtom;

public class ModFunc implements FlameFunction {

    @Override
    public String name() {
        return "Mod";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 2) {
            throw new FlameArityException(name(), 2, args.size());
        }

        Expr a = args.get(0);
        Expr b = args.get(1);

        if (a.isNumeric() && b.isNumeric()) {
            if (b.isZero()) {
                throw new ArithmeticException("Mod: division by zero");
            }

            // Integer/Rational % Integer/Rational → exact rational arithmetic
            if ((a instanceof IntegerAtom || a instanceof RationalAtom)
                    && (b instanceof IntegerAtom || b instanceof RationalAtom)) {
                RationalAtom ra = RationalAtom.rationalOf(a);
                RationalAtom rb = RationalAtom.rationalOf(b);
                long an = ((IntegerAtom) ra.num()).value().toLong();
                long ad = ((IntegerAtom) ra.denom()).value().toLong();
                long bn = ((IntegerAtom) rb.num()).value().toLong();
                long bd = ((IntegerAtom) rb.denom()).value().toLong();

                long floor = Math.floorDiv(an * bd, ad * bn);

                long resultNum = an * bd - floor * ad * bn;
                long resultDenom = ad * bd;

                return RationalAtom.of(resultNum, resultDenom);
            }

            // Otherwise → Real
            double da = numericValue(a);
            double db = numericValue(b);
            double result = da % db;
            if (result != 0 && (result > 0) != (db > 0)) {
                result += db;
            }
            return new RealAtom(result);
        }

        return new Compound(name(), args);
    }
}
