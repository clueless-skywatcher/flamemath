package io.flamemath.expr;

import java.util.List;

public sealed interface Expr
        permits IntegerAtom, RealAtom, ComplexAtom, RationalAtom,
                StringAtom, Symbol, BooleanAtom,
                NullExpr, Compound, Flambda, ListExpr,
                DictExpr, DictEntryExpr, VariadicArgument {

    boolean isAtomic();

    default List<Expr> getChildren() {
        return List.of();
    }
    
    default boolean isNumeric() {
        return false;
    }

    default boolean isZero() {
        return this.equals(IntegerAtom.ZERO) || this.equals(new RealAtom(0.0));
    }

    default boolean isOne() {
        return this.equals(IntegerAtom.ONE) || this.equals(new RealAtom(1.0));
    }

    default boolean isInteger() {
        return this instanceof IntegerAtom;
    }

    default boolean isPositive() {
        if (this instanceof IntegerAtom i) return i.value().signum() > 0;
        if (this instanceof RealAtom r) return r.value() > 0;
        if (this instanceof RationalAtom rat
                && rat.num() instanceof IntegerAtom n
                && rat.denom() instanceof IntegerAtom d) {
            return n.value().signum() * d.value().signum() > 0;
        }
        return false;
    }

    default boolean isNegative() {
        if (this instanceof IntegerAtom i) return i.value().isNegative();
        if (this instanceof RealAtom r) return r.value() < 0;
        if (this instanceof RationalAtom rat
                && rat.num() instanceof IntegerAtom n
                && rat.denom() instanceof IntegerAtom d) {
            return n.value().signum() * d.value().signum() < 0;
        }
        return false;
    }

    String head();

    default boolean isHead(String head) {
        return head().equals(head);
    }

    default boolean isTrue() {
        return false;
    }

    default boolean isFalse() {
        return false;
    }

    int hash();
}
