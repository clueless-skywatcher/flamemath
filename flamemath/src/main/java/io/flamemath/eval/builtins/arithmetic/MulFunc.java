package io.flamemath.eval.builtins.arithmetic;

import static io.flamemath.FlameUtils.numericValue;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.expr.CanonicalComparator;
import io.flamemath.expr.Compound;
import io.flamemath.expr.Expr;
import io.flamemath.expr.IntegerAtom;
import io.flamemath.expr.RealAtom;

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

        boolean hasReal = false;
        double numericProduct = 1.0;

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
                if (args.get(i) instanceof RealAtom) {
                    hasReal = true;
                }
                numericProduct *= numericValue(args.get(i));
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

        if (numericProduct != 1 || results.isEmpty()) {
            Expr num = hasReal ? new RealAtom(numericProduct) : new IntegerAtom((long) numericProduct);
            results.addFirst(num);
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
