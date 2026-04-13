package io.flamemath.expr;

import java.util.List;

import io.flamemath.internal.math.FlameInt;

public record RationalAtom(Expr num, Expr denom) implements Expr {

    @Override
    public boolean isAtomic() {
        return true;
    }

    @Override
    public boolean isNumeric() {
        return true;
    }

    @Override
    public List<Expr> getChildren() {
        return List.of(num, denom);
    }

    @Override
    public String head() {
        return "Rational";
    }

    @Override
    public int hash() {
        return toString().hashCode();
    }

    @Override
    public final String toString() {
        return String.format("(%s/%s)", num.toString(), denom.toString());
    }

    public Expr reduce() {
        if (num instanceof IntegerAtom numI && denom instanceof IntegerAtom denomI) {
            FlameInt n = numI.value();
            FlameInt d = denomI.value();

            FlameInt gcd = n.gcd(d);
            n = n.divide(gcd);
            d = d.divide(gcd);
            if (d.isNegative()) {
                n = n.negate();
                d = d.negate();
            }

            if (d.isOne()) {
                return new IntegerAtom(n);
            }
            return new RationalAtom(new IntegerAtom(n), new IntegerAtom(d));
        }

        return this;
    }

    public static Expr of(long num, long denom) {
        return new RationalAtom(new IntegerAtom(num), new IntegerAtom(denom)).reduce();
    }

    public static RationalAtom rationalOf(Expr expr) throws Exception {
        if (expr instanceof IntegerAtom i) 
            return new RationalAtom(i, IntegerAtom.ONE);
        else if (expr instanceof RationalAtom r)
            return r;
        throw new Exception("Cannot convert to rational");
    }
}
