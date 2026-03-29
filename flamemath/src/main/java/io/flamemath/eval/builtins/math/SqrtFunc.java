package io.flamemath.eval.builtins.math;

import java.util.ArrayList;
import java.util.List;

import io.flamemath.FlameUtils;
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

        // Sqrt(n) → extract largest perfect-square factor
        if (arg instanceof IntegerAtom intArg) {
            long val = intArg.value().toLong();
            if (val < 0) {
                return new Compound(name(), args);
            }
            if (val == 0) return IntegerAtom.ZERO;
            long[] rootAndRem = FlameUtils.extractSquareRoot(val);
            long root = rootAndRem[0];
            long remainder = rootAndRem[1];
            if (remainder == 1) return new IntegerAtom(root);
            Expr sqrtRem = new Compound("Pow",
                    List.of(new IntegerAtom(remainder), RationalAtom.of(1, 2)));
            if (root == 1) return sqrtRem;
            return evaluator.eval(
                    new Compound("Mul", List.of(new IntegerAtom(root), sqrtRem)));
        }

        // Sqrt(p/q) → delegate to Pow
        if (arg instanceof RationalAtom) {
            return evaluator.eval(
                    new Compound("Pow", List.of(arg, RationalAtom.of(1, 2))));
        }

        if (arg instanceof RealAtom r) {
            return FlameUtils.toNumericAtom(Math.sqrt(r.value()));
        }

        // Sqrt(Mul(a, b, ...)) → extract numeric perfect-square factors
        if (arg instanceof Compound c && c.head().equals("Mul")) {
            long intProduct = 1;
            List<Expr> symbolicFactors = new ArrayList<>();
            for (Expr child : c.children()) {
                if (child instanceof IntegerAtom intChild) {
                    intProduct *= intChild.value().toLong();
                } else {
                    symbolicFactors.add(child);
                }
            }
            if (intProduct > 1) {
                long[] rootAndRem = FlameUtils.extractSquareRoot(intProduct);
                long root = rootAndRem[0];
                long remainder = rootAndRem[1];
                if (root > 1) {
                    if (remainder != 1) {
                        symbolicFactors.add(0, new IntegerAtom(remainder));
                    }
                    Expr underRadical;
                    if (symbolicFactors.isEmpty()) {
                        return new IntegerAtom(root);
                    } else if (symbolicFactors.size() == 1) {
                        underRadical = symbolicFactors.get(0);
                    } else {
                        underRadical = new Compound("Mul", symbolicFactors);
                    }
                    Expr sqrtPart = new Compound(name(), List.of(underRadical));
                    return evaluator.eval(
                            new Compound("Mul", List.of(new IntegerAtom(root), sqrtPart)));
                }
            }
        }

        return new Compound(name(), args);
    }

    @Override
    public Expr numerify(List<Expr> args) throws Exception {
        return new RealAtom(Math.sqrt(io.flamemath.FlameUtils.numericValue(args.get(0))));
    }
}
