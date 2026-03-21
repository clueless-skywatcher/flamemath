package io.flamemath.expr;

public record RationalAtom(Expr num, Expr denom) implements Expr {
    private long gcd(long a, long b) {
        a = Math.abs(a);
        b = Math.abs(b);
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    @Override
    public boolean isAtomic() {
        return true;
    }

    @Override
    public boolean isNumeric() {
        return true;
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
            long numlong = numI.value();
            long denomlong = denomI.value();

            long gcd = gcd(numlong, denomlong);
            numlong /= gcd;
            denomlong /= gcd;
            if (denomlong < 0) {
                numlong = -numlong;
                denomlong = -denomlong;
            }

            if (denomlong == 1) {
                return new IntegerAtom(numlong);
            }
            return new RationalAtom(new IntegerAtom(numlong), new IntegerAtom(denomlong));
        }

        return this;
    }

    public static Expr of(long num, long denom) {
        return new RationalAtom(new IntegerAtom(num), new IntegerAtom(denom)).reduce();
    }

    public static RationalAtom rationalOf(Expr expr) throws Exception {
        if (expr instanceof IntegerAtom i) 
            return new RationalAtom(new IntegerAtom(i.value()), IntegerAtom.ONE);
        else if (expr instanceof RationalAtom r)
            return r;
        throw new Exception("Cannot convert to rational");
    }
}
