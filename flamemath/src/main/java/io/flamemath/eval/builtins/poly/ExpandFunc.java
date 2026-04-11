package io.flamemath.eval.builtins.poly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.flamemath.FlameUtils;
import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.Compound;
import io.flamemath.expr.Expr;
import io.flamemath.expr.IntegerAtom;
import io.flamemath.internal.FlameInt;

public class ExpandFunc implements FlameFunction {

    @Override
    public String name() {
        return "Expand";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 1) {
            throw new FlameArityException(name(), 1, args.size());
        }

        Expr expr = args.get(0);
        return expandExpr(expr, evaluator);
    }

    private Expr expandExpr(Expr expr, FlameValuator evaluator) throws Exception {
        if (expr.isAtomic())
            return expr;
        if (expr.isHead("Pow")) {
            Expr base = expandExpr(expr.getChildren().get(0), evaluator);
            Expr exp = expr.getChildren().get(1);
            if (base.isHead("Add") && exp.isInteger() && exp.isPositive()) {
                FlameInt value = ((IntegerAtom) exp).value();
                if (value.fitsInLong() && value.toLong() <= Integer.MAX_VALUE) {
                    return multinomialExpand(base.getChildren(), (int) value.toLong(), evaluator);
                }
            }
            return evaluator.eval(new Compound("Pow", List.of(base, exp)));
        }

        if (expr.isHead("Mul")) {
            List<Expr> expandedFactors = new ArrayList<>();
            for (var factor : expr.getChildren()) {
                expandedFactors.add(expandExpr(factor, evaluator));
            }
            return distribute(expandedFactors, evaluator);
        }
        if (expr.isHead("Add")) {
            List<Expr> expandedFactors = new ArrayList<>();
            for (var factor : expr.getChildren()) {
                expandedFactors.add(expandExpr(factor, evaluator));
            }
            return evaluator.eval(new Compound("Add", expandedFactors));
        }

        return expr;
    }

    private Expr distribute(List<Expr> factors, FlameValuator evaluator) throws Exception {
        List<List<Expr>> factorLists = new ArrayList<>();
        for (var factor: factors) {
            if (factor.isHead("Add")) {
                factorLists.add(factor.getChildren());
            } else {
                factorLists.add(List.of(factor));
            }
        }

        List<List<Expr>> products = cartesianProduct(factorLists);
        
        List<Expr> terms = new ArrayList<>();
        for (var tuple: products) {
            Expr term = evaluator.eval(new Compound("Mul", tuple));
            terms.add(term);
        }
        
        return evaluator.eval(new Compound("Add", terms));
    }

    private List<List<Expr>> cartesianProduct(List<List<Expr>> factorLists) {
        List<List<Expr>> result = List.of(List.of());
        for (var list: factorLists) {
            List<List<Expr>> newResult = new ArrayList<>();
            for (var partial: result) {
                for (var elem: list) {
                    List<Expr> newTuple = new ArrayList<>(partial);
                    newTuple.add(elem);
                    newResult.add(newTuple); 
                }
            }
            result = newResult;
        }
        return result;
    }

    private Expr multinomialExpand(List<Expr> terms, int n, FlameValuator evaluator) throws Exception {
        List<Expr> allTerms = new ArrayList<>();
        for (int[] composition : compositions(n, terms.size())) {
            FlameInt coeff = multinomialCoeff(n, composition);
            List<Expr> factors = new ArrayList<>();
            factors.add(new IntegerAtom(coeff));

            for (int i = 0; i < terms.size(); i++) {
                if (composition[i] > 0) {
                    factors.add(new Compound("Pow", List.of(terms.get(i), new IntegerAtom(composition[i]))));
                }
            }
            allTerms.add(evaluator.eval(new Compound("Mul", factors)));
        }
        return evaluator.eval(new Compound("Add", allTerms));
    }

    /**
     * Enumerate all compositions of n into k non-negative parts.
     *
     * Each composition is an int[] of length k where all elements sum to n.
     * Used by multinomialExpand to iterate over all multinomial terms.
     *
     * Example: compositions(2, 3) yields:
     *   [2,0,0], [1,1,0], [1,0,1], [0,2,0], [0,1,1], [0,0,2]
     */
    private List<int[]> compositions(int n, int k) {
        if (k == 1) return List.of(new int[]{n});

        List<int[]> results = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            for (int[] rest : compositions(n - i, k - 1)) {
                results.add(concatWithIntArrayCopy(new int[]{i}, rest));
            }
        }

        return results;
    }

    private FlameInt multinomialCoeff(int n, int[] exponents) {
        return FlameUtils.multinomialCoeff(n, exponents);
    }

    static int[] concatWithIntArrayCopy(int[] array1, int[] array2) {
        int[] result = Arrays.copyOf(array1, array1.length + array2.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        return result;
    }
}
