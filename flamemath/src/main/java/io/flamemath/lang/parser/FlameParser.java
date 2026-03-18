package io.flamemath.lang.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.flamemath.eval.FlameVironment;
import io.flamemath.expr.BooleanAtom;
import io.flamemath.expr.Compound;
import io.flamemath.expr.Expr;
import io.flamemath.expr.Flambda;
import io.flamemath.expr.IntegerAtom;
import io.flamemath.expr.RealAtom;
import io.flamemath.expr.StringAtom;
import io.flamemath.expr.Symbol;
import io.flamemath.lang.FMToken;
import io.flamemath.lang.FMTokenType;
import io.flamemath.lang.lexer.FMLexer;

public class FlameParser {
    private final List<FMToken> tokens;
    private int pos = 0;

    private static final Map<FMTokenType, Integer> PRECEDENCE = Map.ofEntries(
        // Semicolon (compound expression)
        Map.entry(FMTokenType.SEMICOLON, 1),

        // Assignment
        Map.entry(FMTokenType.EQUAL, 2),

        // Logical OR
        Map.entry(FMTokenType.PIPE_PIPE, 5),

        // Logical AND
        Map.entry(FMTokenType.AMP_AMP, 6),

        // Equality
        Map.entry(FMTokenType.EQUAL_EQUAL, 7),
        Map.entry(FMTokenType.BANG_EQUAL, 7),

        // Comparison
        Map.entry(FMTokenType.LESS, 8),
        Map.entry(FMTokenType.LESS_EQUAL, 8),
        Map.entry(FMTokenType.GREATER, 8),
        Map.entry(FMTokenType.GREATER_EQUAL, 8),

        // Addition / subtraction
        Map.entry(FMTokenType.PLUS, 9),
        Map.entry(FMTokenType.MINUS, 9),

        // Multiplication / division
        Map.entry(FMTokenType.STAR, 10),
        Map.entry(FMTokenType.SLASH, 10),

        // Exponentiation (right-associative)
        Map.entry(FMTokenType.CARET, 11),

        // Indexing — high so expr[i] binds tightly
        Map.entry(FMTokenType.LBRACKET, 12)
    );

    // Right-associative operators use (prec - 1) for the recursive call
    private static final Set<FMTokenType> RIGHT_ASSOC = Set.of(
        FMTokenType.CARET,
        FMTokenType.EQUAL
    );

    // Maps operator tokens to their Compound head names
    private static final Map<FMTokenType, String> HEAD = Map.ofEntries(
        Map.entry(FMTokenType.PLUS, "Add"),
        Map.entry(FMTokenType.MINUS, "Add"),       // a - b → Plus(a, Times(-1, b))
        Map.entry(FMTokenType.STAR, "Mul"),
        Map.entry(FMTokenType.SLASH, "Mul"),       // a / b → Times(a, Power(b, -1))
        Map.entry(FMTokenType.CARET, "Pow"),

        Map.entry(FMTokenType.EQUAL_EQUAL, "Eq"),
        Map.entry(FMTokenType.BANG_EQUAL, "NotEq"),
        Map.entry(FMTokenType.LESS, "Less"),
        Map.entry(FMTokenType.LESS_EQUAL, "LessEq"),
        Map.entry(FMTokenType.GREATER, "Greater"),
        Map.entry(FMTokenType.GREATER_EQUAL, "GreaterEq"),

        Map.entry(FMTokenType.AMP_AMP, "And"),
        Map.entry(FMTokenType.PIPE_PIPE, "Or"),

        Map.entry(FMTokenType.EQUAL, "Set"),
        Map.entry(FMTokenType.SEMICOLON, "Seq"),

        Map.entry(FMTokenType.LBRACKET, "At")
    );

    private int precedenceOf(FMToken token) {
        return PRECEDENCE.getOrDefault(token.type(), 0);
    }

    public FlameParser(String code) throws Exception {
        this.tokens = new FMLexer(code).lex();
    }

    public FMToken peek() {
        if (pos < tokens.size()) {
            return tokens.get(pos);
        }
        return tokens.getLast();
    }

    public FMToken advance() {
        if (pos < tokens.size()) {
            FMToken token = tokens.get(pos);
            pos++;
            return token;
        } else {
            return new FMToken(FMTokenType.EOF, "", pos, pos);
        }
    }

    public FMToken expect(FMTokenType type) throws Exception {
        FMToken peek = peek();
        if (peek.type() != type) {
            throw new Exception(String.format("Expected %s but got %s", type, peek));
        }
        return advance();
    }

    public Expr parse() throws Exception {
        return parseExpr(0);
    }

    private Expr parseExpr(int minPrecedence) throws Exception {
        Expr left = parsePrefix();

        while (precedenceOf(peek()) > minPrecedence) {
            left = parseInfix(left);
        }

        return left;
    }

    private Expr parseInfix(Expr left) throws Exception {
        FMToken operator = advance();
        if (operator.type() == FMTokenType.MINUS) {
            Expr right = parseExpr(precedenceOf(operator));
            return new Compound(
                "Add",
                List.of(
                    left,
                    new Compound("Mul", 
                        List.of(
                            IntegerAtom.MINUS_ONE,
                            right
                        )
                    )
                )
            );
        } else if (operator.type() == FMTokenType.SLASH) {
            Expr right = parseExpr(precedenceOf(operator));
            return new Compound(
                "Mul",
                List.of(
                    left,
                    new Compound("Pow", 
                        List.of(
                            right,
                            IntegerAtom.MINUS_ONE
                        )
                    )
                )
            );
        } else if (operator.type() == FMTokenType.LBRACKET) {
            Expr index = parseExpr(0);
            expect(FMTokenType.RBRACKET);
            return new Compound(
                "At", List.of(left, index)
            );
        }
        
        int precedence = precedenceOf(operator);
        if (RIGHT_ASSOC.contains(operator.type())) {
            precedence = precedence - 1;
        }
        Expr right = parseExpr(precedence);
        return new Compound(HEAD.get(operator.type()), List.of(left, right));
    }

    private Expr parsePrefix() throws Exception {
        FMToken token = advance();
        return switch (token.type()) {
            case IDENT -> {
                String name = token.value();
                if (peek().type() == FMTokenType.LPAREN) {
                    advance();
                    List<Expr> args = parseCommaSeparated(FMTokenType.RPAREN);
                    expect(FMTokenType.RPAREN);
                    yield new Compound(name, args);
                } else if (name.equals("True")) {
                    yield new BooleanAtom(true);
                } else if (name.equals("False")) {
                    yield new BooleanAtom(false);
                } else {
                    yield new Symbol(name);
                }
            }
            case INTEGER -> new IntegerAtom(Long.parseLong(token.value()));
            case REAL -> new RealAtom(Double.parseDouble(token.value()));
            case STRING -> new StringAtom(token.value());
            case LPAREN -> {
                List<Expr> params = parseCommaSeparated(FMTokenType.RPAREN);
                expect(FMTokenType.RPAREN);
                if (peek().type() == FMTokenType.FAT_ARROW) {
                    List<Symbol> symbols = new ArrayList<>();
                    for (var param: params) {
                        if (param.isHead("Symbol")) {
                            symbols.add((Symbol) param);
                        } else {
                            throw new Exception("Lambda parameters contain only Symbols");
                        }
                    }
                    expect(FMTokenType.FAT_ARROW);
                    Expr body = parseExpr(1);
                    yield new Flambda(symbols, body, new FlameVironment());
                } else if (params.size() == 1) {
                    yield params.get(0);
                } else if (params.size() > 1) {
                    throw new Exception("Parsing error for Lambda");
                } else {
                    throw new Exception("Unexpected empty parentheses");
                }
            }
            case LBRACKET -> {
                List<Expr> items = parseCommaSeparated(FMTokenType.RBRACKET);
                expect(FMTokenType.RBRACKET);
                yield new Compound("List", items);
            }
            case MINUS -> {
                Expr operand = parseExpr(10); // bind tighter than +/- but looser than ^
                yield new Compound("Mul", List.of(IntegerAtom.MINUS_ONE, operand));
            }
            case BANG -> {
                Expr operand = parseExpr(10);
                yield new Compound("Not", List.of(operand));
            }
            case LBRACE -> {
                Expr body = parseExpr(0);
                expect(FMTokenType.RBRACE);
                yield body;
            }
            default -> throw new Exception("Unexpected token in prefix position: " + token);
        };
    }

    private List<Expr> parseCommaSeparated(FMTokenType closer) throws Exception {
        if (peek().type() == closer) {
            return new ArrayList<>();
        }

        List<Expr> items = new ArrayList<>();
        items.add(parseExpr(0));

        while (peek().type() == FMTokenType.COMMA) {
            advance();
            items.add(parseExpr(0));
        }

        return items;
    }
}
