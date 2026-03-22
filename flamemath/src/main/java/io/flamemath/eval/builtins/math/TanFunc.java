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

public class TanFunc implements FlameFunction {

    @Override
    public String name() {
        return "Tan";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 1) {
            throw new FlameArityException(name(), 1, args.size());
        }

        Expr arg = args.get(0);

        // Numeric → compute directly
        if (arg instanceof IntegerAtom i) {
            return toNumericAtom(Math.tan(i.value()));
        }
        if (arg instanceof RealAtom r) {
            return toNumericAtom(Math.tan(r.value()));
        }

        // Delegate: Tan(x) = Sin(x) / Cos(x)
        long[] rationalCoeff = CosFunc.extractPiCoeffs(arg);
        if (rationalCoeff != null) {
            Expr sinResult = evaluator.eval(new Compound("Sin", List.of(arg)));
            Expr cosResult = evaluator.eval(new Compound("Cos", List.of(arg)));

            // Both resolved to exact values
            if (!(sinResult instanceof Compound && sinResult.isHead("Sin"))
                    && !(cosResult instanceof Compound && cosResult.isHead("Cos"))) {
                if (cosResult.isZero()) {
                    throw new ArithmeticException("Tan undefined: division by zero at " + arg);
                }
                if (sinResult.isZero()) {
                    return IntegerAtom.ZERO;
                }
                // sin / cos
                return evaluator.eval(
                        new Compound("Mul", List.of(sinResult,
                                new Compound("Pow", List.of(cosResult, IntegerAtom.MINUS_ONE)))));
            }
        }

        return new Compound(name(), args);
    }

    @Override
    public Expr numerify(List<Expr> args) throws Exception {
        return toNumericAtom(Math.tan(io.flamemath.FlameUtils.numericValue(args.get(0))));
    }
}
