package io.flamemath.expr;

import static io.flamemath.FlameUtils.numericValue;

import java.util.Comparator;
import java.util.Map;

public class CanonicalComparator implements Comparator<Expr> {
    public static final CanonicalComparator INSTANCE = new CanonicalComparator();
    private static final Map<String, Integer> TYPE_RANK = Map.of(
        "Integer", 0,
        "Real", 0,
        "Complex", 0,
        "Symbol", 1,
        "String", 3,
        "Boolean", 3
    );

    @Override
    public int compare(Expr arg0, Expr arg1) {
        int rank0 = TYPE_RANK.getOrDefault(arg0.head(), 2);
        int rank1 = TYPE_RANK.getOrDefault(arg1.head(), 2);

        if (rank0 != rank1) {
            return rank0 - rank1;
        }

        switch (rank0) {
            case 0:
                return Double.compare(numericValue(arg0), numericValue(arg1));
            case 1:
                return arg0.toString().compareTo(arg1.toString());
            case 2:
                Compound c0 = (Compound) arg0;
                Compound c1 = (Compound) arg1;

                int headComp = c0.head().compareTo(c1.head());
                if (headComp != 0) {
                    return headComp;
                }
                int minimumLen = Math.min(c0.children().size(), c1.children().size());
                for (int i = 0; i < minimumLen; i++) {
                    int childComp = compare(c0.children().get(i), c1.children().get(i));
                    if (childComp != 0) return childComp;
                }

                return c0.children().size() - c1.children().size();
            default:
                return arg0.toString().compareTo(arg1.toString());
        }
    }
    
}
