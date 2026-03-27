package io.flamemath.eval.builtins.math;

import java.util.List;
import java.util.Map;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.Compound;
import io.flamemath.expr.Expr;
import io.flamemath.expr.IntegerAtom;
import io.flamemath.expr.RationalAtom;
import io.flamemath.expr.RealAtom;
import io.flamemath.expr.Symbol;
import static io.flamemath.FlameUtils.toNumericAtom;
import io.flamemath.FlameUtils;
import io.flamemath.internal.FlameInt;

public class ArcTanFunc implements FlameFunction {

    private static final Expr PI = new Symbol("Pi");

    // ArcTan special values in [0, Pi/2):
    // ArcTan(0)=0, ArcTan(1/Sqrt(3))=Pi/6, ArcTan(1)=Pi/4, ArcTan(Sqrt(3))=Pi/3
    // Key format uses normalized string representations
    private static final Map<String, Expr> SPECIAL_TABLE = Map.of(
        "0",         IntegerAtom.ZERO,                                           // ArcTan(0) = 0
        "1/Sqrt(3)", new Compound("Mul", List.of(RationalAtom.of(1, 6), PI)),    // ArcTan(1/Sqrt(3)) = Pi/6
        "1",         new Compound("Mul", List.of(RationalAtom.of(1, 4), PI)),    // ArcTan(1) = Pi/4
        "Sqrt(3)",   new Compound("Mul", List.of(RationalAtom.of(1, 3), PI))     // ArcTan(Sqrt(3)) = Pi/3
    );

    @Override
    public String name() {
        return "ArcTan";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 1) {
            throw new FlameArityException(name(), 1, args.size());
        }

        Expr arg = args.get(0);

        // Numeric → compute directly
        if (arg instanceof IntegerAtom i) {
            return toNumericAtom(Math.atan(i.value().toDouble()));
        }
        if (arg instanceof RealAtom r) {
            return toNumericAtom(Math.atan(r.value()));
        }

        // Check for special symbolic values
        Expr result = lookupSpecialValue(arg, evaluator);
        if (result != null) {
            return evaluator.eval(result);
        }

        return new Compound(name(), args);
    }

    private Expr lookupSpecialValue(Expr arg, FlameValuator evaluator) throws Exception {
        // Detect negative
        boolean negative = false;
        Expr absArg = arg;

        if (arg instanceof RationalAtom r
                && r.num() instanceof IntegerAtom rn
                && rn.value().isNegative()) {
            negative = true;
            absArg = new RationalAtom(new IntegerAtom(rn.value().negate()), r.denom());
        } else if (arg instanceof IntegerAtom i && i.value().isNegative()) {
            negative = true;
            absArg = new IntegerAtom(i.value().negate());
        } else if (arg instanceof Compound c && c.isHead("Mul")
                && !c.children().isEmpty()) {
            Expr first = c.children().get(0);
            if (first instanceof IntegerAtom i && i.value().equals(FlameInt.MINUS_ONE)) {
                negative = true;
                List<Expr> rest = c.children().subList(1, c.children().size());
                absArg = rest.size() == 1 ? rest.get(0) : new Compound("Mul", rest);
            } else if (first instanceof RationalAtom r
                    && r.num() instanceof IntegerAtom rn && rn.value().isNegative()) {
                negative = true;
                Expr posRational = new RationalAtom(new IntegerAtom(rn.value().negate()), r.denom());
                java.util.List<Expr> newChildren = new java.util.ArrayList<>();
                newChildren.add(posRational);
                newChildren.addAll(c.children().subList(1, c.children().size()));
                absArg = newChildren.size() == 1 ? newChildren.get(0) : new Compound("Mul", newChildren);
            } else if (first instanceof IntegerAtom i && i.value().isNegative()) {
                negative = true;
                java.util.List<Expr> newChildren = new java.util.ArrayList<>();
                newChildren.add(new IntegerAtom(i.value().negate()));
                newChildren.addAll(c.children().subList(1, c.children().size()));
                absArg = newChildren.size() == 1 ? newChildren.get(0) : new Compound("Mul", newChildren);
            }
        }

        String key = normalizeArcTanKey(absArg);
        Expr value = key != null ? SPECIAL_TABLE.get(key) : null;

        if (value == null) return null;

        if (negative) {
            if (value.isZero()) return IntegerAtom.ZERO;
            return new Compound("Mul", List.of(IntegerAtom.MINUS_ONE, value));
        }
        return value;
    }

    /**
     * Normalize an expression to a lookup key for the ArcTan special table.
     * Recognizes: 0, 1, Sqrt(3), and Pow(3, -1/2) i.e. 1/Sqrt(3).
     */
    private String normalizeArcTanKey(Expr arg) {
        if (arg instanceof IntegerAtom i) {
            if (i.value().isZero()) return "0";
            if (i.value().equals(FlameInt.ONE)) return "1";
            return null;
        }
        // Match Sqrt(3) or Pow(3, 1/2)
        long sqrtBase = ArcSinFunc.isSqrt(arg);
        if (sqrtBase == 3) return "Sqrt(3)";

        // Match Pow(3, -1/2) which is 1/Sqrt(3)
        if (arg instanceof Compound c && c.isHead("Pow") && c.children().size() == 2) {
            Expr base = c.children().get(0);
            Expr exp = c.children().get(1);
            if (base instanceof IntegerAtom bi && bi.value().toLong() == 3
                    && exp instanceof RationalAtom r
                    && r.num() instanceof IntegerAtom rn && rn.value().equals(FlameInt.MINUS_ONE)
                    && r.denom() instanceof IntegerAtom rd && rd.value().toLong() == 2) {
                return "1/Sqrt(3)";
            }
        }
        // Match Mul((1/3), Sqrt(3)) or Mul((1/3), Pow(3, (1/2))) = Sqrt(3)/3 = 1/Sqrt(3)
        if (arg instanceof Compound c && c.isHead("Mul") && c.children().size() == 2) {
            Expr first = c.children().get(0);
            Expr second = c.children().get(1);
            if (first instanceof RationalAtom r
                    && r.num() instanceof IntegerAtom rn && rn.value().equals(FlameInt.ONE)
                    && r.denom() instanceof IntegerAtom rd && rd.value().toLong() == 3
                    && ArcSinFunc.isSqrt(second) == 3) {
                return "1/Sqrt(3)";
            }
        }
        return null;
    }

    @Override
    public Expr numerify(List<Expr> args) throws Exception {
        return toNumericAtom(Math.atan(FlameUtils.numericValue(args.get(0))));
    }
}
