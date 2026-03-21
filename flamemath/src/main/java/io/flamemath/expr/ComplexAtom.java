package io.flamemath.expr;

public record ComplexAtom(double re, double im) implements Expr {
    @Override public boolean isAtomic() { return true; }

    @Override
    public String head() {
        return "Complex";
    }

    @Override
    public String toString() {
        if (im == 0) return Double.toString(re);
        if (re == 0) return im + "i";
        return re + (im >= 0 ? " + " : " - ") + Math.abs(im) + "i";
    }

    @Override
    public boolean isNumeric() {
        return true;
    }

    @Override
    public int hash() {
        return 31 * Double.hashCode(re) + Double.hashCode(im);
    }
}
