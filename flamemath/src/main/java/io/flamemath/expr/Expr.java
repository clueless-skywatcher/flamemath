package io.flamemath.expr;

public sealed interface Expr
        permits IntegerAtom, RealAtom, ComplexAtom,
                StringAtom, Symbol, BooleanAtom,
                Compound {

    boolean isAtomic();
    
    default boolean isNumeric() {
        return false;
    }

    String head();
}
