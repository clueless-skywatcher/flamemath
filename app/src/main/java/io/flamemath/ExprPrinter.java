package io.flamemath;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import io.flamemath.expr.BooleanAtom;
import io.flamemath.expr.Compound;
import io.flamemath.expr.Expr;
import io.flamemath.expr.IntegerAtom;
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
        Map.entry("Multi", ";")
    );

    // Infix operators: head name → precedence (must match parser)
    private static final Map<String, Integer> INFIX_PREC = Map.ofEntries(
        Map.entry("Multi", 1),
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
        Map.entry("Pow", 11)
    );

    private static final int HIGHEST_PRECEDENCE = 100;

    // Right-associative infix operators
    private static final Set<String> RIGHT_ASSOC = Set.of("Pow", "Set");

    // Prefix operators: head name → symbol string
    private static final Map<String, String> PREFIX_SYMBOL = Map.of(
        "Not", "!"
    );

    public static String print(Expr expr, int outerPrecedence) {
        switch (expr) {
            case IntegerAtom(long l):
                if (l < 0) {
                    return "(" + l + ")";
                }
                return Long.toString(l);
            case RealAtom(double r):
                return Double.toString(r);
            case StringAtom(String s):
                return '"' + s + '"';
            case Symbol(String s):
                return s;
            case BooleanAtom(boolean b):
                return b ? "True" : "False";
            case Compound(String head, List<Expr> children): {
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
                    String.join(", ", args)
                );
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
            sb.append(print(children.get(0), myPrec));
            for (int i = 1; i < children.size(); i++) {
                Expr child = children.get(i);
                // Detect Mul(-1, x) → print as " - x"
                if (child instanceof Compound c
                        && c.head().equals("Mul")
                        && c.children().size() == 2
                        && c.children().get(0).equals(new IntegerAtom(-1))) {
                    sb.append(" - ");
                    sb.append(print(c.children().get(1), myPrec));
                } else {
                    sb.append(" + ");
                    sb.append(print(child, myPrec));
                }
            }
        } else if (head.equals("Mul")) {
            sb.append(printMulTerm(children.get(0), myPrec, true));
            for (int i = 1; i < children.size(); i++) {
                Expr child = children.get(i);
                // Detect Pow(x, -1) → print as " / x"
                if (child instanceof Compound c
                        && c.head().equals("Pow")
                        && c.children().size() == 2
                        && c.children().get(0) instanceof Expr base
                        && c.children().get(1).equals(new IntegerAtom(-1))) {
                    sb.append(" / ");
                    sb.append(print(base, myPrec));
                } else {
                    sb.append(" * ");
                    sb.append(print(child, myPrec));
                }
            }
        } else {
            // Generic binary infix
            int leftPrec = myPrec;
            int rightPrec = rightAssoc ? myPrec - 1 : myPrec;

            sb.append(print(children.get(0), leftPrec));
            for (int i = 1; i < children.size(); i++) {
                sb.append(" ").append(INFIX_SYMBOL.get(head)).append(" ");
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
                && c.children().get(0).equals(new IntegerAtom(-1))) {
            return "-" + print(c.children().get(1), mulPrec);
        }
        return print(expr, mulPrec);
    }
}
