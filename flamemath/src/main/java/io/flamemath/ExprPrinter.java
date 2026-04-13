package io.flamemath;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import static io.flamemath.FlameUtils.*;

import io.flamemath.expr.BooleanAtom;
import io.flamemath.expr.comparators.GradedLexComparator;
import io.flamemath.internal.math.FlameInt;
import io.flamemath.expr.Compound;
import io.flamemath.expr.DictExpr;
import io.flamemath.expr.Expr;
import io.flamemath.expr.IntegerAtom;
import io.flamemath.expr.RationalAtom;
import io.flamemath.expr.ListExpr;
import io.flamemath.expr.Flambda;
import io.flamemath.expr.NullExpr;
import io.flamemath.expr.RealAtom;
import io.flamemath.expr.StringAtom;
import io.flamemath.expr.Symbol;

public class ExprPrinter {

    // Infix operators: head name → symbol string
    private static final Map<String, String> INFIX_SYMBOL = Map.ofEntries(
            Map.entry("Add", "+"),
            Map.entry("Mul", "*"),
            Map.entry("Pow", "^"),
            Map.entry("Eq", "=="),
            Map.entry("NotEq", "!="),
            Map.entry("Less", "<"),
            Map.entry("LessEq", "<="),
            Map.entry("Greater", ">"),
            Map.entry("GreaterEq", ">="),
            Map.entry("And", "&&"),
            Map.entry("Or", "||"),
            Map.entry("Set", "="),
            Map.entry("Seq", ";"));

    // Infix operators: head name → precedence (must match parser)
    private static final Map<String, Integer> INFIX_PREC = Map.ofEntries(
            Map.entry("Seq", 1),
            Map.entry("Set", 2),
            Map.entry("Or", 5),
            Map.entry("And", 6),
            Map.entry("Eq", 7),
            Map.entry("NotEq", 7),
            Map.entry("Less", 8),
            Map.entry("LessEq", 8),
            Map.entry("Greater", 8),
            Map.entry("GreaterEq", 8),
            Map.entry("Add", 9),
            Map.entry("Mul", 10),
            Map.entry("Pow", 11));

    private static final int HIGHEST_PRECEDENCE = 100;

    // Right-associative infix operators
    private static final Set<String> RIGHT_ASSOC = Set.of("Pow", "Set");

    // Operators printed without surrounding spaces
    private static final Set<String> TIGHT_INFIX = Set.of("Mul", "Pow");

    // Mul factor display order: alphabetical by base variable name.
    // Symbol "x" uses "x", Pow(x, n) uses "x", anything else falls back to toString.
    private static final Comparator<Expr> MUL_DISPLAY_ORDER = (a, b) -> {
        String nameA = mulFactorBaseName(a);
        String nameB = mulFactorBaseName(b);
        return nameA.compareTo(nameB);
    };

    private static String mulFactorBaseName(Expr expr) {
        if (expr instanceof Symbol s) return s.name();
        if (expr instanceof Compound c && c.head().equals("Pow")
                && !c.children().isEmpty()
                && c.children().getFirst() instanceof Symbol s) {
            return s.name();
        }
        return expr.toString();
    }

    // Prefix operators: head name → symbol string
    private static final Map<String, String> PREFIX_SYMBOL = Map.of(
            "Not", "!");

    public static String print(Expr expr, int outerPrecedence) {
        switch (expr) {
            case IntegerAtom(FlameInt fi):
                return fi.toString();
            case RealAtom(double r):
                return Double.toString(r);
            case StringAtom(String s):
                return '"' + s + '"';
            case Symbol(String s):
                return s;
            case BooleanAtom(boolean b):
                return b ? "True" : "False";
            case NullExpr ignored:
                return "Null";
            case Flambda f:
                return "Lambda<>";
            case ListExpr l: {
                StringJoiner joiner = new StringJoiner(", ");
                for (var exp : l.exprs()) {
                    joiner.add(print(exp));
                }
                return "[" + joiner.toString() + "]";
            }
            case DictExpr dict:
                StringJoiner joiner = new StringJoiner(", ");
                for (var entry : dict.dict().entrySet()) {
                    joiner.add(
                        String.format("%s: %s", 
                            print(entry.getKey()),
                            print(entry.getValue())
                        )
                    );
                }
                return "{" + joiner.toString() +  "}";
            case Compound(String head, List<Expr> children): {
                if (head.equals("Seq") && children.stream().allMatch(c -> c instanceof Flambda)) {
                    return "Lambda<" + children.size() + " clauses>";
                }
                // Sqrt(...) → √(...)
                if (head.equals("Sqrt") && children.size() == 1) {
                    return printSqrt(children.get(0));
                }
                // Pow(x, (1/2)) → √x
                if (head.equals("Pow") && children.size() == 2 && isRationalHalf(children.get(1))) {
                    return printSqrt(children.get(0));
                }
                if (INFIX_SYMBOL.containsKey(head)) {
                    return printInfix(head, children, outerPrecedence);
                }
                if (PREFIX_SYMBOL.containsKey(head)) {
                    return PREFIX_SYMBOL.get(head) + print(children.get(0), HIGHEST_PRECEDENCE);
                }
                List<String> args = children.stream()
                        .map(c -> print(c, 0))
                        .collect(Collectors.toList());
                return String.format("%s(%s)",
                        head,
                        String.join(", ", args));
            }
            default:
                return expr.toString();
        }
    }

    public static String print(Expr expr) {
        return print(expr, 0);
    }

    private static String printInfix(String head, List<Expr> children, int outerPrecedence) {
        int myPrec = INFIX_PREC.get(head);
        boolean rightAssoc = RIGHT_ASSOC.contains(head);

        StringBuilder sb = new StringBuilder();

        if (head.equals("Add")) {
            List<Expr> display = new ArrayList<>(children);
            display.sort(GradedLexComparator.INSTANCE);
            sb.append(print(display.get(0), myPrec));
            for (int i = 1; i < display.size(); i++) {
                Expr child = display.get(i);
                // Detect Mul(-1, x) → print as " - x"
                if (child instanceof Compound c
                        && c.head().equals("Mul")
                        && !c.children().isEmpty()
                        && isNegativeNumeric(c.children().getFirst())) {
                    sb.append(" - ");
                    double absCoeff = Math.abs(numericValue(c.children().getFirst()));
                    List<Expr> rest = c.children().subList(1, c.children().size());
                    if (absCoeff == 1) {
                        // Mul(-1, x, y) → "- x*y"
                        if (rest.size() == 1)
                            sb.append(print(rest.getFirst(), myPrec));
                        else
                            sb.append(print(new Compound("Mul", rest), myPrec));
                    } else {
                        // Mul(-2, x, y) → "- 2*x*y"
                        List<Expr> negated = new ArrayList<>();
                        negated.add(toNumericAtom(absCoeff));
                        negated.addAll(rest);
                        sb.append(print(new Compound("Mul", negated), myPrec));
                    }
                } else {
                    sb.append(" + ");
                    sb.append(print(child, myPrec));
                }
            }
        } else if (head.equals("Mul")) {
            List<Expr> display = new ArrayList<>(children);
            // Keep numeric coefficient first, sort the rest by base variable name
            if (!display.isEmpty() && display.getFirst().isNumeric()) {
                List<Expr> rest = display.subList(1, display.size());
                rest.sort(MUL_DISPLAY_ORDER);
            } else {
                display.sort(MUL_DISPLAY_ORDER);
            }
            sb.append(printMulTerm(display.get(0), myPrec, true));

            for (int i = 1; i < display.size(); i++) {
                Expr child = display.get(i);
                // Detect Pow(x, -1) → print as " / x"
                if (child instanceof Compound c
                        && c.head().equals("Pow")
                        && c.children().size() == 2
                        && c.children().get(0) instanceof Expr base
                        && c.children().get(1).equals(IntegerAtom.MINUS_ONE)) {
                    sb.append(" / ");
                    sb.append(print(base, myPrec));
                } else {
                    sb.append("*");
                    sb.append(print(child, myPrec));
                }
            }
        } else {
            // Generic binary infix
            int leftPrec = myPrec;
            int rightPrec = rightAssoc ? myPrec - 1 : myPrec;

            sb.append(print(children.get(0), leftPrec));
            String sep = TIGHT_INFIX.contains(head)
                    ? INFIX_SYMBOL.get(head)
                    : " " + INFIX_SYMBOL.get(head) + " ";
            for (int i = 1; i < children.size(); i++) {
                sb.append(sep);
                sb.append(print(children.get(i), rightPrec));
            }
        }

        String result = sb.toString();
        if (outerPrecedence > myPrec) {
            return "(" + result + ")";
        }
        return result;
    }

    private static String printMulTerm(Expr expr, int mulPrec, boolean isFirst) {
        // For the first term in a Mul, check for Mul(-1, x) → "-x"
        if (isFirst
                && expr instanceof Compound c
                && c.head().equals("Mul")
                && c.children().size() == 2
                && c.children().get(0).equals(IntegerAtom.MINUS_ONE)) {
            return "-" + print(c.children().get(1), mulPrec);
        }
        return print(expr, mulPrec);
    }

    private static boolean isRationalHalf(Expr expr) {
        return expr instanceof RationalAtom r
                && r.num() instanceof IntegerAtom rn && rn.value().isOne()
                && r.denom() instanceof IntegerAtom rd && rd.value().equals(new FlameInt(2));
    }

    private static String printSqrt(Expr arg) {
        // Simple atoms don't need parens: √2, √x
        if (arg.isAtomic()) {
            return "√" + print(arg, HIGHEST_PRECEDENCE);
        }
        // Compound expressions need parens: √(x^2), √(2*x)
        return "√(" + print(arg, 0) + ")";
    }
}
