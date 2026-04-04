package io.flamemath.eval.builtins.arithmetic;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.expr.*;

import static io.flamemath.FlameUtils.*;

import io.flamemath.internal.FlameInt;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AddFunc implements FlameFunction {
    @Override
    public String name() {
        return "Add";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() == 0) {
            return IntegerAtom.ZERO;
        }
        if (args.size() == 1) {
            return args.get(0);
        }

        // Decompose all args and check for reals
        List<Expr[]> decomposed = new ArrayList<>();
        boolean hasReal = false;

        for (Expr arg : args) {
            List<Expr> dec = decomposeAdditive(arg);
            Expr coeff = dec.get(0);
            Expr core = dec.get(1);
            if (coeff instanceof RealAtom) {
                hasReal = true;
            }
            decomposed.add(new Expr[]{coeff, core});
        }

        if (hasReal) {
            return applyDouble(decomposed);
        }
        return applyExact(decomposed);
    }

    private Expr applyExact(List<Expr[]> decomposed) {
        Map<Expr, FlameInt[]> argsToCoeffs = new LinkedHashMap<>();

        for (Expr[] pair : decomposed) {
            Expr coeff = pair[0];
            Expr core = pair[1];

            FlameInt num;
            FlameInt denom;
            if (coeff instanceof RationalAtom r
                    && r.num() instanceof IntegerAtom rn
                    && r.denom() instanceof IntegerAtom rd) {
                num = rn.value();
                denom = rd.value();
            } else if (coeff instanceof IntegerAtom i) {
                num = i.value();
                denom = FlameInt.ONE;
            } else {
                num = FlameInt.ONE;
                denom = FlameInt.ONE;
            }

            FlameInt[] existing = argsToCoeffs.get(core);
            if (existing != null) {
                // a/b + c/d = (a*d + c*b) / (b*d)
                FlameInt newNum = existing[0].mul(denom).add(num.mul(existing[1]));
                FlameInt newDenom = existing[1].mul(denom);
                argsToCoeffs.put(core, new FlameInt[]{newNum, newDenom});
            } else {
                argsToCoeffs.put(core, new FlameInt[]{num, denom});
            }
        }

        // Extract and remove the pure numeric sum before iterating symbolic terms
        FlameInt[] numericPair = argsToCoeffs.getOrDefault(IntegerAtom.ONE,
                new FlameInt[]{FlameInt.ZERO, FlameInt.ONE});
        argsToCoeffs.remove(IntegerAtom.ONE);

        List<Expr> results = new ArrayList<>();

        for (var entry : argsToCoeffs.entrySet()) {
            FlameInt[] nd = entry.getValue();
            Expr reduced = new RationalAtom(new IntegerAtom(nd[0]), new IntegerAtom(nd[1])).reduce();
            if (reduced.isZero()) continue;
            Expr core = entry.getKey();
            if (reduced.isOne()) {
                results.add(core);
            } else {
                results.add(buildMul(reduced, core));
            }
        }

        // Prepend numeric constant if nonzero, or if there are no symbolic terms
        Expr numericConstant = new RationalAtom(
                new IntegerAtom(numericPair[0]), new IntegerAtom(numericPair[1])).reduce();
        if (!numericConstant.isZero() || results.isEmpty()) {
            results.addFirst(numericConstant);
        }

        if (results.isEmpty()) return IntegerAtom.ZERO;
        if (results.size() == 1) return results.getFirst();
        results.sort(CanonicalComparator.INSTANCE);
        return new Compound("Add", results);
    }

    private Expr applyDouble(List<Expr[]> decomposed) {
        Map<Expr, Double> argsToCoeffs = new LinkedHashMap<>();

        for (Expr[] pair : decomposed) {
            double coeff = numericValue(pair[0]);
            Expr core = pair[1];
            argsToCoeffs.merge(core, coeff, Double::sum);
        }

        double numericSum = argsToCoeffs.getOrDefault(IntegerAtom.ONE, 0.0);
        argsToCoeffs.remove(IntegerAtom.ONE);

        List<Expr> results = new ArrayList<>();

        for (var entry : argsToCoeffs.entrySet()) {
            double coeff = entry.getValue();
            if (coeff == 0) continue;
            Expr core = entry.getKey();
            if (coeff == 1) results.add(core);
            else results.add(buildMul(toNumericAtom(coeff), core));
        }

        if (numericSum != 0 || results.isEmpty()) {
            results.addFirst(new RealAtom(numericSum));
        }

        if (results.isEmpty()) return IntegerAtom.ZERO;
        if (results.size() == 1) return results.getFirst();
        results.sort(CanonicalComparator.INSTANCE);
        return new Compound("Add", results);
    }

    @Override
    public boolean isFlat() {
        return true;
    }

    private Compound buildMul(Expr coeff, Expr core) {
        List<Expr> children = new ArrayList<>();
        children.add(coeff);
        if (core instanceof Compound comp && comp.head().equals("Mul")) {
            children.addAll(comp.children());
        } else {
            children.add(core);
        }
        return new Compound("Mul", children);
    }

    private List<Expr> decomposeAdditive(Expr expr) {
        if (expr instanceof IntegerAtom || expr instanceof RealAtom || expr instanceof RationalAtom) {
            return List.of(expr, IntegerAtom.ONE);
        }
        if (expr instanceof Compound comp
                && comp.head().equals("Mul")
                && !comp.children().isEmpty()
                && (comp.children().getFirst() instanceof IntegerAtom
                    || comp.children().getFirst() instanceof RealAtom 
                    || comp.children().getFirst() instanceof RationalAtom)) {
            Expr coeff = comp.children().getFirst();
            List<Expr> rest = comp.children().subList(1, comp.children().size());
            Expr core;
            if (rest.size() == 1) {
                core = rest.getFirst();
            } else {
                List<Expr> sorted = new ArrayList<>(rest);
                sorted.sort(CanonicalComparator.INSTANCE);
                core = new Compound("Mul", sorted);
            }
            return List.of(coeff, core);
        }
        return List.of(IntegerAtom.ONE, expr);
    }
}
