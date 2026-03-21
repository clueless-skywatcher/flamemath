package io.flamemath.expr;

public record RealAtom(double value) implements Expr {
    @Override public boolean isAtomic() { return true; }

    @Override
    public String head() {
        return "Real";
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }

    @Override
    public boolean isNumeric() {
        return true;
    }

    @Override
    public int hash() {
        return Double.hashCode(value);
    }

    public static Expr rationalize(RealAtom r) {
        String[] realBroken = r.toString().split("\\.");
        int decimals = realBroken[1].length();
        long denom = (long) Math.pow(10, decimals);
        long num = (long) (r.value() * denom);
        return RationalAtom.of(num, denom);
    }
}
