package io.flamemath.eval.builtins.poly;

import java.util.List;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.Compound;
import io.flamemath.expr.Expr;
import io.flamemath.expr.IntegerAtom;
import io.flamemath.expr.Symbol;
import io.flamemath.internal.math.FlameInt;

public class DegreeFunc implements FlameFunction {

    @Override
    public String name() {
        return "Degree";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 2) {
            throw new FlameArityException(name(), 2, args.size());
        }

        Expr expr = args.get(0);
        Expr varArg = args.get(1);

        if (!(varArg instanceof Symbol)) {
            return new Compound(name(), args);
        }

        return new IntegerAtom(walkDegree(expr, varArg));
    }

    private FlameInt walkDegree(Expr expr, Expr varArg) {
        if (expr instanceof Symbol eS && varArg instanceof Symbol vS) {
            if (eS.equals(vS)) return FlameInt.ONE;
            return FlameInt.ZERO;
        }
        if (expr.isNumeric()) return FlameInt.ZERO;
        if (expr.isHead("Pow")) {
            Compound pow = (Compound) expr;
            Expr base = pow.children().get(0);
            Expr expo = pow.children().get(1);

            if (expo instanceof IntegerAtom i) {
                return walkDegree(base, varArg).mul(i.value());
            }
            return FlameInt.ZERO;
        }
        if (expr.isHead("Mul")) {
            Compound mul = (Compound) expr;
            FlameInt degree = FlameInt.ZERO;
            for (var c : mul.children()) {
                degree = degree.add(walkDegree(c, varArg));
            }
            return degree;
        }
        if (expr.isHead("Add")) {
            Compound add = (Compound) expr;
            FlameInt maxValue = FlameInt.ZERO;
            for (var c: add.children()) {
                FlameInt degree = walkDegree(c, varArg);
                if (degree.compareTo(maxValue) > 0) {
                    maxValue = degree;
                }
            }

            return maxValue;
        }

        return FlameInt.ZERO;
    }
}
