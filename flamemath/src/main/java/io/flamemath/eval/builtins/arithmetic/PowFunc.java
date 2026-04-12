package io.flamemath.eval.builtins.arithmetic;

import static io.flamemath.FlameUtils.numericValue;

import java.util.ArrayList;
import java.util.List;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.Compound;
import io.flamemath.expr.Expr;
import io.flamemath.expr.IntegerAtom;
import io.flamemath.expr.RationalAtom;
import io.flamemath.expr.RealAtom;
import io.flamemath.internal.FlameInt;

public class PowFunc implements FlameFunction {

    @Override
    public String name() {
        return "Pow";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 2) {
            throw new FlameArityException("Pow", 2, args.size());
        }

        Expr base = args.get(0);
        Expr exp = args.get(1);

        // Integer base with rational exponent 1/2 → perfect square check
        if (base instanceof IntegerAtom baseInt
                && exp instanceof RationalAtom r
                && r.num() instanceof IntegerAtom rn && rn.value().isOne()
                && r.denom() instanceof IntegerAtom rd && rd.value().equals(new FlameInt(2))) {
            long val = baseInt.value().toLong();
            long root = (long) Math.sqrt(val);
            if (root * root == val) {
                return new IntegerAtom(root);
            }
            // Non-perfect square: delegate to Sqrt for simplification
            return evaluator.eval(new Compound("Sqrt", List.of(base)));
        }

        // Both numeric → compute directly
        if (base.isNumeric() && exp.isNumeric()) {
            long expLong = (long) numericValue(exp);
            // Division by zero: 0^(negative) is undefined
            if (base.isZero() && expLong < 0) {
                throw new ArithmeticException("Division by zero");
            }
            // Negative integer exponent on integer base → RationalAtom
            if (base instanceof IntegerAtom baseInt && exp instanceof IntegerAtom && expLong < 0) {
                FlameInt denom = baseInt.value().pow(-expLong);
                return new RationalAtom(IntegerAtom.ONE, new IntegerAtom(denom)).reduce();
            }
            // Negative integer exponent on real base → promote to real
            if (exp instanceof IntegerAtom && expLong < 0) {
                return new RealAtom(Math.pow(numericValue(base), expLong));
            }
            if (base instanceof RealAtom || exp instanceof RealAtom) {
                return new RealAtom(Math.pow(numericValue(base), numericValue(exp)));
            }
            // Integer base, positive integer exponent
            if (base instanceof IntegerAtom baseInt && exp instanceof IntegerAtom) {
                return new IntegerAtom(baseInt.value().pow(expLong));
            }
            // 0^0 → 1 (common convention)
            return new RealAtom(Math.pow(numericValue(base), numericValue(exp)));
        }

        // x^0 → 1
        if (exp.isZero()) {
            return IntegerAtom.ONE;
        }

        // x^1 → x
        if (exp.isOne()) {
            return base;
        }

        // 0^n → 0 (positive n)
        if (base.isZero() && exp.isNumeric() && numericValue(exp) > 0) {
            return IntegerAtom.ZERO;
        }

        // 1^n → 1
        if (base.isOne()) {
            return IntegerAtom.ONE;
        }

        // Pow(Pow(b, e1), e2) → Pow(b, eval(e1 * e2))
        if (base instanceof Compound c && c.head().equals("Pow")) {
            Expr baseOfBase = c.children().get(0);
            Expr expOfBase = c.children().get(1);
            Expr newExp = evaluator.eval(new Compound("Mul", List.of(expOfBase, exp)));
            return evaluator.eval(new Compound("Pow", List.of(baseOfBase, newExp)));
        }

        // Pow(Sqrt(b), e) → Pow(b, eval(e * (1/2)))
        if (base instanceof Compound c && c.head().equals("Sqrt") && c.children().size() == 1) {
            Expr sqrtArg = c.children().get(0);
            Expr newExp = evaluator.eval(new Compound("Mul", List.of(RationalAtom.of(1, 2), exp)));
            return evaluator.eval(new Compound("Pow", List.of(sqrtArg, newExp)));
        }

        // Pow(Mul(a, b, ...), n) → eval(Mul(Pow(a, n), Pow(b, n), ...))
        if (base instanceof Compound c && c.head().equals("Mul") && exp instanceof IntegerAtom) {
            List<Expr> powArgs = new ArrayList<>();
            for (Expr arg : c.children()) {
                powArgs.add(new Compound("Pow", List.of(arg, exp)));
            }
            return evaluator.eval(new Compound("Mul", powArgs));
        }

        return new Compound("Pow", List.of(base, exp));
    }

}
