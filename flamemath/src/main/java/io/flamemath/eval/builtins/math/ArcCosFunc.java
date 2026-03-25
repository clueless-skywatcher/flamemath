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

public class ArcCosFunc implements FlameFunction {

    private static final Expr PI = new Symbol("Pi");

    // ArcCos maps Cos values back to angles in [0, Pi]
    // ArcCos(1)=0, ArcCos(Sqrt(3)/2)=Pi/6, ArcCos(Sqrt(2)/2)=Pi/4,
    // ArcCos(1/2)=Pi/3, ArcCos(0)=Pi/2,
    // ArcCos(-1/2)=2*Pi/3, ArcCos(-Sqrt(2)/2)=3*Pi/4,
    // ArcCos(-Sqrt(3)/2)=5*Pi/6, ArcCos(-1)=Pi
    private static final Map<String, Expr> POS_TABLE = Map.of(
        "0",           new Compound("Mul", List.of(RationalAtom.of(1, 2), PI)),    // ArcCos(0) = Pi/2
        "1/2",         new Compound("Mul", List.of(RationalAtom.of(1, 3), PI)),    // ArcCos(1/2) = Pi/3
        "Sqrt(2)/2",   new Compound("Mul", List.of(RationalAtom.of(1, 4), PI)),    // ArcCos(Sqrt(2)/2) = Pi/4
        "Sqrt(3)/2",   new Compound("Mul", List.of(RationalAtom.of(1, 6), PI)),    // ArcCos(Sqrt(3)/2) = Pi/6
        "1",           IntegerAtom.ZERO                                             // ArcCos(1) = 0
    );

    private static final Map<String, Expr> NEG_TABLE = Map.of(
        "1/2",         new Compound("Mul", List.of(RationalAtom.of(2, 3), PI)),    // ArcCos(-1/2) = 2*Pi/3
        "Sqrt(2)/2",   new Compound("Mul", List.of(RationalAtom.of(3, 4), PI)),    // ArcCos(-Sqrt(2)/2) = 3*Pi/4
        "Sqrt(3)/2",   new Compound("Mul", List.of(RationalAtom.of(5, 6), PI)),    // ArcCos(-Sqrt(3)/2) = 5*Pi/6
        "1",           PI                                                           // ArcCos(-1) = Pi
    );

    @Override
    public String name() {
        return "ArcCos";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 1) {
            throw new FlameArityException(name(), 1, args.size());
        }

        Expr arg = args.get(0);

        // Numeric → compute directly
        if (arg instanceof IntegerAtom i) {
            if (i.value() < -1 || i.value() > 1) {
                throw new ArithmeticException("ArcCos: argument out of range [-1, 1]");
            }
            return toNumericAtom(Math.acos(i.value()));
        }
        if (arg instanceof RealAtom r) {
            if (r.value() < -1 || r.value() > 1) {
                throw new ArithmeticException("ArcCos: argument out of range [-1, 1]");
            }
            return toNumericAtom(Math.acos(r.value()));
        }

        // Check for special symbolic values
        Expr result = lookupSpecialValue(arg);
        if (result != null) {
            return evaluator.eval(result);
        }

        return new Compound(name(), args);
    }

    private Expr lookupSpecialValue(Expr arg) {
        // Detect negative: if arg is Mul(-1, ...), negative rational, or Mul(negRational, ...)
        boolean negative = false;
        Expr absArg = arg;

        if (arg instanceof RationalAtom r
                && r.num() instanceof IntegerAtom rn
                && rn.value() < 0) {
            negative = true;
            absArg = new RationalAtom(new IntegerAtom(-rn.value()), r.denom());
        } else if (arg instanceof IntegerAtom i && i.value() < 0) {
            negative = true;
            absArg = new IntegerAtom(-i.value());
        } else if (arg instanceof Compound c && c.isHead("Mul")
                && !c.children().isEmpty()) {
            Expr first = c.children().get(0);
            if (first instanceof IntegerAtom i && i.value() == -1) {
                negative = true;
                List<Expr> rest = c.children().subList(1, c.children().size());
                absArg = rest.size() == 1 ? rest.get(0) : new Compound("Mul", rest);
            } else if (first instanceof RationalAtom r
                    && r.num() instanceof IntegerAtom rn && rn.value() < 0) {
                negative = true;
                Expr posRational = new RationalAtom(new IntegerAtom(-rn.value()), r.denom());
                java.util.List<Expr> newChildren = new java.util.ArrayList<>();
                newChildren.add(posRational);
                newChildren.addAll(c.children().subList(1, c.children().size()));
                absArg = newChildren.size() == 1 ? newChildren.get(0) : new Compound("Mul", newChildren);
            } else if (first instanceof IntegerAtom i && i.value() < 0) {
                negative = true;
                java.util.List<Expr> newChildren = new java.util.ArrayList<>();
                newChildren.add(new IntegerAtom(-i.value()));
                newChildren.addAll(c.children().subList(1, c.children().size()));
                absArg = newChildren.size() == 1 ? newChildren.get(0) : new Compound("Mul", newChildren);
            }
        }

        String key = ArcSinFunc.normalizeKey(absArg);
        if (key == null) return null;

        if (negative) {
            return NEG_TABLE.get(key);
        } else {
            return POS_TABLE.get(key);
        }
    }

    @Override
    public Expr numerify(List<Expr> args) throws Exception {
        return toNumericAtom(Math.acos(FlameUtils.numericValue(args.get(0))));
    }
}
