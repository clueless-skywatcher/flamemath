package io.flamemath.eval.builtins.arithmetic;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.expr.*;

import static io.flamemath.FlameUtils.*;

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

        Map<Expr, Double> argsToCoeffs = new LinkedHashMap<>();
        
        boolean hasReal = false;

        for (Expr arg : args) {
            List<Expr> decomposed = decomposeAdditive(arg);
            double coeff = numericValue(decomposed.get(0));
            Expr core = decomposed.get(1);

            if (decomposed.get(0) instanceof RealAtom) hasReal = true;
            argsToCoeffs.merge(core, coeff, Double::sum);
        }

        // Extract and remove the pure numeric sum before iterating symbolic terms
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

        // Prepend numeric constant if nonzero, or if there are no symbolic terms
        if (numericSum != 0 || results.isEmpty()) {
            Expr num = hasReal ? new RealAtom(numericSum) : new IntegerAtom((long) numericSum);
            results.addFirst(num);
        }

        if (results.isEmpty()) return IntegerAtom.ZERO;
        if (results.size() == 1) return results.getFirst();

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
        if (expr instanceof IntegerAtom || expr instanceof RealAtom) {
            return List.of(expr, IntegerAtom.ONE);
        }
        if (expr instanceof Compound comp
                && comp.head().equals("Mul")
                && !comp.children().isEmpty()
                && (comp.children().getFirst() instanceof IntegerAtom
                    || comp.children().getFirst() instanceof RealAtom)) {
            Expr coeff = comp.children().getFirst();
            List<Expr> rest = comp.children().subList(1, comp.children().size());
            Expr core = rest.size() == 1 ? rest.getFirst() : new Compound("Mul", rest);
            return List.of(coeff, core);
        }
        return List.of(IntegerAtom.ONE, expr);
    }
}
