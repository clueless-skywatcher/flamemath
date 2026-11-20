package io.flamemath.expr.comparators;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import io.flamemath.expr.Compound;
import io.flamemath.expr.Expr;
import io.flamemath.expr.IntegerAtom;
import io.flamemath.expr.Symbol;

public class GradedLexComparator implements Comparator<Expr> {
    public static final GradedLexComparator INSTANCE = new GradedLexComparator();

    @Override
    public int compare(Expr a, Expr b) {
        // 1. Negative-coefficient terms after positive
        boolean aNeg = isNegativeTerm(a);
        boolean bNeg = isNegativeTerm(b);
        if (aNeg != bNeg) return aNeg ? 1 : -1;

        // 2. Collect variables from both terms
        Set<Symbol> varSet = new TreeSet<>(Comparator.comparing(Symbol::name));
        collectSymbols(a, varSet);
        collectSymbols(b, varSet);

        // 3. No variables — fall back to safe comparison
        if (varSet.isEmpty()) {
            return safeCompare(a, b);
        }

        List<Symbol> vars = new ArrayList<>(varSet);

        // 4. Extract exponent vectors
        int[] vecA = extractExponents(a, vars);
        int[] vecB = extractExponents(b, vars);

        // 5. Total degree descending
        int degA = 0, degB = 0;
        for (int e : vecA) degA += e;
        for (int e : vecB) degB += e;
        if (degA != degB) return degB - degA;

        // 6. Lexicographic tiebreak descending
        for (int i = 0; i < vars.size(); i++) {
            if (vecA[i] != vecB[i]) return vecB[i] - vecA[i];
        }

        // 7. Fallback
        return safeCompare(a, b);
    }

    private int safeCompare(Expr a, Expr b) {
        if (a instanceof Compound && b instanceof Compound) {
            return CanonicalComparator.INSTANCE.compare(a, b);
        }
        return a.toString().compareTo(b.toString());
    }

    private boolean isNegativeTerm(Expr expr) {
        return expr instanceof Compound c
                && c.head().equals("Mul")
                && !c.children().isEmpty()
                && c.children().getFirst().isNegative();
    }

    private void collectSymbols(Expr expr, Set<Symbol> symbols) {
        if (expr instanceof Symbol s) {
            symbols.add(s);
        }
        for (var child: expr.getChildren()) {
            collectSymbols(child, symbols);
        }
    }

    private int[] extractExponents(Expr term, List<Symbol> vars) {
        int[] vec = new int[vars.size()];

        if (term instanceof Symbol s) {
            int idx = vars.indexOf(s);
            if (idx >= 0) vec[idx] = 1;
        } else if (term instanceof Compound c && c.head().equals("Pow")
                && c.children().size() == 2
                && c.children().get(0) instanceof Symbol s
                && c.children().get(1) instanceof IntegerAtom ia
                && ia.value().signum() > 0
                && ia.value().fitsInLong()) {
            int idx = vars.indexOf(s);
            if (idx >= 0) vec[idx] = (int) ia.value().toLong();
        } else if (term instanceof Compound c && c.head().equals("Mul")) {
            for (Expr child : c.children()) {
                if (!child.isNumeric()) {
                    int[] childVec = extractExponents(child, vars);
                    for (int i = 0; i < vars.size(); i++) {
                        vec[i] += childVec[i];
                    }
                }
            }
        }

        return vec;
    }
}
