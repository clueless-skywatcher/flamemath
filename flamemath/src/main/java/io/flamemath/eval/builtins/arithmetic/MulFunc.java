package io.flamemath.eval.builtins.arithmetic;

import static io.flamemath.FlameUtils.numericValue;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.expr.Compound;
import io.flamemath.expr.Expr;
import io.flamemath.expr.IntegerAtom;
import io.flamemath.expr.RationalAtom;
import io.flamemath.expr.RealAtom;
import io.flamemath.expr.comparators.CanonicalComparator;
import io.flamemath.internal.FlameInt;

public class MulFunc implements FlameFunction {
    @Override
    public String name() {
        return "Mul";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() == 0) {
            return IntegerAtom.ZERO;
        }
        if (args.size() == 1) {
            return args.get(0);
        }

        // Rationalize: Mul(integers..., RationalAtom...) → RationalAtom
        FlameInt numerator = FlameInt.ONE;
        FlameInt denominator = FlameInt.ONE;
        boolean isRational = true;
        boolean hasRational = false;

        for (Expr arg : args) {
            if (arg instanceof IntegerAtom i) {
                numerator = numerator.mul(i.value());
            } else if (arg instanceof RationalAtom r
                    && r.num() instanceof IntegerAtom rNum
                    && r.denom() instanceof IntegerAtom rDenom) {
                numerator = numerator.mul(rNum.value());
                denominator = denominator.mul(rDenom.value());
                hasRational = true;
            } else {
                isRational = false;
                break;
            }
        }

        if (isRational && hasRational) {
            return new RationalAtom(new IntegerAtom(numerator), new IntegerAtom(denominator)).reduce();
        }

        boolean hasReal = false;
        boolean hasRatCoeff = false;
        FlameInt intNum = FlameInt.ONE;
        FlameInt intDenom = FlameInt.ONE;
        double realProduct = 1.0;

        List<Expr> results = new ArrayList<>();

        Map<Expr, Expr> baseToExp = new LinkedHashMap<>();

        for (int i = 0; i < args.size(); i++) {
            if (args.get(i).isZero()) {
                return IntegerAtom.ZERO;
            }
            if (!args.get(i).isNumeric()) {
                List<Expr> baseAndExp = decomposeMultiplicative(args.get(i));
                if (baseToExp.containsKey(baseAndExp.get(0))) {
                    Expr existing = baseToExp.get(baseAndExp.get(0));
                    baseToExp.put(baseAndExp.get(0), evaluator.eval(
                            new Compound(
                                    "Add", List.of(existing, baseAndExp.get(1)))));
                } else {
                    baseToExp.put(baseAndExp.get(0), baseAndExp.get(1));
                }
            } else {
                Expr a = args.get(i);
                if (a instanceof RealAtom) {
                    hasReal = true;
                    realProduct *= numericValue(a);
                } else if (a instanceof RationalAtom r
                        && r.num() instanceof IntegerAtom rn
                        && r.denom() instanceof IntegerAtom rd) {
                    hasRatCoeff = true;
                    intNum = intNum.mul(rn.value());
                    intDenom = intDenom.mul(rd.value());
                } else if (a instanceof IntegerAtom ia) {
                    intNum = intNum.mul(ia.value());
                }
            }
        }

        for (var entry : baseToExp.entrySet()) {
            Expr exp = entry.getValue();
            Expr base = entry.getKey();

            if (exp.isZero()) {
                continue;
            } else if (exp.isOne()) {
                results.add(base);
            } else {
                results.add(evaluator.eval(new Compound("Pow", List.of(base, exp))));
            }
        }

        double numericProduct = hasReal ? realProduct * (intNum.toDouble() / intDenom.toDouble()) : 0;

        if (hasReal) {
            if (numericProduct != 1 || results.isEmpty()) {
                results.addFirst(new RealAtom(numericProduct));
            }
        } else {
            Expr coeff = hasRatCoeff
                    ? new RationalAtom(new IntegerAtom(intNum), new IntegerAtom(intDenom)).reduce()
                    : new IntegerAtom(intNum);
            if (!coeff.isOne() || results.isEmpty()) {
                results.addFirst(coeff);
            }
        }

        if (results.size() == 1) {
            return results.getFirst();
        }
        results.sort(CanonicalComparator.INSTANCE);
        return new Compound("Mul", results);
    }

    @Override
    public boolean isFlat() {
        return true;
    }

    private List<Expr> decomposeMultiplicative(Expr expr) {
        if (expr instanceof Compound comp
                && comp.head().equals("Pow")
                && !comp.children().isEmpty()) {
            Expr base = comp.children().get(0);
            Expr exp = comp.children().get(1);
            return List.of(base, exp);
        }
        return List.of(expr, IntegerAtom.ONE);
    }
}
