package io.flamemath.expr;

public sealed interface Expr
        permits IntegerAtom, RealAtom, ComplexAtom,
                StringAtom, Symbol, BooleanAtom,
                NullExpr, Compound, Flambda {

    boolean isAtomic();
    
    default boolean isNumeric() {
        return false;
    }

    default boolean isZero() {
        return this.equals(IntegerAtom.ZERO) || this.equals(new RealAtom(0.0));
    }

    default boolean isOne() {
        return this.equals(IntegerAtom.ONE) || this.equals(new RealAtom(1.0));
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
}
