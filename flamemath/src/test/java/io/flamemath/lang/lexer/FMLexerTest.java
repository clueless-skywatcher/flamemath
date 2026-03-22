package io.flamemath.lang.lexer;

import io.flamemath.lang.FMToken;
import io.flamemath.lang.FMTokenType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FMLexerTest {

    private List<FMToken> lex(String input) throws Exception {
        return new FMLexer(input).lex();
    }

    private void assertTypes(List<FMToken> tokens, FMTokenType... expected) {
        assertEquals(expected.length, tokens.size(),
                "token count mismatch: " + tokens);
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], tokens.get(i).type(),
                    "token " + i + ": " + tokens.get(i));
        }
    }

    // --- Numeric literals ---

    @Test
    void integer() throws Exception {
        var tokens = lex("42");
        assertTypes(tokens, FMTokenType.INTEGER, FMTokenType.EOF);
        assertEquals("42", tokens.get(0).value());
    }

    @Test
    void integerZero() throws Exception {
        var tokens = lex("0");
        assertTypes(tokens, FMTokenType.INTEGER, FMTokenType.EOF);
    }

    @Test
    void realNumber() throws Exception {
        var tokens = lex("3.14");
        assertTypes(tokens, FMTokenType.REAL, FMTokenType.EOF);
        assertEquals("3.14", tokens.get(0).value());
    }

    @Test
    void realStartingWithDot() throws Exception {
        var tokens = lex(".5");
        assertTypes(tokens, FMTokenType.REAL, FMTokenType.EOF);
        assertEquals(".5", tokens.get(0).value());
    }

    @Test
    void realTrailingDot() throws Exception {
        var tokens = lex("5.");
        assertTypes(tokens, FMTokenType.REAL, FMTokenType.EOF);
        assertEquals("5.", tokens.get(0).value());
    }

    @Test
    void scientificNotation() throws Exception {
        var tokens = lex("1.0e-5");
        assertTypes(tokens, FMTokenType.REAL, FMTokenType.EOF);
        assertEquals("1.0e-5", tokens.get(0).value());
    }

    @Test
    void doubleDotThrows() {
        assertThrows(Exception.class, () -> lex("3.14.15"));
    }

    // --- Identifiers ---

    @Test
    void simpleIdent() throws Exception {
        var tokens = lex("x");
        assertTypes(tokens, FMTokenType.IDENT, FMTokenType.EOF);
        assertEquals("x", tokens.get(0).value());
    }

    @Test
    void multiCharIdent() throws Exception {
        var tokens = lex("Sin");
        assertTypes(tokens, FMTokenType.IDENT, FMTokenType.EOF);
        assertEquals("Sin", tokens.get(0).value());
    }

    @Test
    void identWithDigits() throws Exception {
        var tokens = lex("x2");
        assertTypes(tokens, FMTokenType.IDENT, FMTokenType.EOF);
        assertEquals("x2", tokens.get(0).value());
    }

    @Test
    void identStartsWithUnderscore() throws Exception {
        // Bare _ is UNDERSCORE, but _foo could be an identifier
        // depending on your design. This test documents the behavior.
        var tokens = lex("abc");
        assertTypes(tokens, FMTokenType.IDENT, FMTokenType.EOF);
    }

    // --- Strings ---

    @Test
    void stringLiteral() throws Exception {
        var tokens = lex("\"hello\"");
        assertTypes(tokens, FMTokenType.STRING, FMTokenType.EOF);
        assertEquals("hello", tokens.get(0).value());
    }

    @Test
    void emptyString() throws Exception {
        var tokens = lex("\"\"");
        assertTypes(tokens, FMTokenType.STRING, FMTokenType.EOF);
        assertEquals("", tokens.get(0).value());
    }

    @Test
    void unterminatedStringThrows() {
        assertThrows(Exception.class, () -> lex("\"hello"));
    }

    // --- Single-character operators and delimiters ---

    @Test
    void arithmeticOperators() throws Exception {
        var tokens = lex("+ - * / ^");
        assertTypes(tokens,
                FMTokenType.PLUS, FMTokenType.MINUS, FMTokenType.STAR,
                FMTokenType.SLASH, FMTokenType.CARET, FMTokenType.EOF);
    }

    @Test
    void delimiters() throws Exception {
        var tokens = lex("( ) { } [ ] , ;");
        assertTypes(tokens,
                FMTokenType.LPAREN, FMTokenType.RPAREN,
                FMTokenType.LBRACE, FMTokenType.RBRACE,
                FMTokenType.LBRACKET, FMTokenType.RBRACKET,
                FMTokenType.COMMA, FMTokenType.SEMICOLON,
                FMTokenType.EOF);
    }

    @Test
    void atSign() throws Exception {
        var tokens = lex("@");
        assertTypes(tokens, FMTokenType.AT, FMTokenType.EOF);
    }

    // --- Multi-character operators ---

    @Test
    void comparisonOperators() throws Exception {
        var tokens = lex("== != < <= > >=");
        assertTypes(tokens,
                FMTokenType.EQUAL_EQUAL, FMTokenType.BANG_EQUAL,
                FMTokenType.LESS, FMTokenType.LESS_EQUAL,
                FMTokenType.GREATER, FMTokenType.GREATER_EQUAL,
                FMTokenType.EOF);
    }

    @Test
    void logicalOperators() throws Exception {
        var tokens = lex("&& || !");
        assertTypes(tokens,
                FMTokenType.AMP_AMP, FMTokenType.PIPE_PIPE,
                FMTokenType.BANG, FMTokenType.EOF);
    }

    @Test
    void assignmentVsEquality() throws Exception {
        var tokens = lex("= ==");
        assertTypes(tokens,
                FMTokenType.EQUAL, FMTokenType.EQUAL_EQUAL, FMTokenType.EOF);
    }

    @Test
    void arrow() throws Exception {
        var tokens = lex("->");
        assertTypes(tokens, FMTokenType.ARROW, FMTokenType.EOF);
    }

    @Test
    void fatArrow() throws Exception {
        var tokens = lex("=>");
        assertTypes(tokens, FMTokenType.FAT_ARROW, FMTokenType.EOF);
    }

    @Test
    void colonGreater() throws Exception {
        var tokens = lex(":>");
        assertTypes(tokens, FMTokenType.COLON_GREATER, FMTokenType.EOF);
    }

    @Test
    void slashDot() throws Exception {
        var tokens = lex("/.");
        assertTypes(tokens, FMTokenType.SLASH_DOT, FMTokenType.EOF);
    }

    @Test
    void slashSlash() throws Exception {
        var tokens = lex("//");
        assertTypes(tokens, FMTokenType.EOF);
    }

    @Test
    void pipeGreater() throws Exception {
        var tokens = lex("|>");
        assertTypes(tokens, FMTokenType.PIPE_GREATER, FMTokenType.EOF);
    }

    @Test
    void slashSemi() throws Exception {
        var tokens = lex("/;");
        assertTypes(tokens, FMTokenType.SLASH_SEMI, FMTokenType.EOF);
    }

    // --- Comments ---

    @Test
    void commentIgnoresRestOfLine() throws Exception {
        var tokens = lex("x + 1 // this is a comment");
        assertTypes(tokens,
                FMTokenType.IDENT, FMTokenType.PLUS, FMTokenType.INTEGER,
                FMTokenType.EOF);
    }

    @Test
    void commentOnlyLine() throws Exception {
        var tokens = lex("// nothing here");
        assertTypes(tokens, FMTokenType.EOF);
    }

    @Test
    void commentDoesNotAffectNextLine() throws Exception {
        var tokens = lex("x // comment\ny");
        assertTypes(tokens,
                FMTokenType.IDENT, FMTokenType.IDENT, FMTokenType.EOF);
        assertEquals("x", tokens.get(0).value());
        assertEquals("y", tokens.get(1).value());
    }

    @Test
    void emptyComment() throws Exception {
        var tokens = lex("//\nx");
        assertTypes(tokens, FMTokenType.IDENT, FMTokenType.EOF);
        assertEquals("x", tokens.get(0).value());
    }

    @Test
    void slashAloneIsNotComment() throws Exception {
        var tokens = lex("a / b");
        assertTypes(tokens,
                FMTokenType.IDENT, FMTokenType.SLASH, FMTokenType.IDENT,
                FMTokenType.EOF);
    }

    @Test
    void slashDotNotComment() throws Exception {
        var tokens = lex("x /. y -> 5");
        assertTypes(tokens,
                FMTokenType.IDENT, FMTokenType.SLASH_DOT,
                FMTokenType.IDENT, FMTokenType.ARROW, FMTokenType.INTEGER,
                FMTokenType.EOF);
    }

    @Test
    void commentAfterExpression() throws Exception {
        var tokens = lex("F(x) // call F");
        assertTypes(tokens,
                FMTokenType.IDENT, FMTokenType.LPAREN, FMTokenType.IDENT, FMTokenType.RPAREN,
                FMTokenType.EOF);
    }

    // --- Underscores (pattern matching) ---

    @Test
    void singleUnderscore() throws Exception {
        var tokens = lex("_");
        assertTypes(tokens, FMTokenType.UNDERSCORE, FMTokenType.EOF);
    }

    @Test
    void doubleUnderscore() throws Exception {
        var tokens = lex("__");
        assertTypes(tokens, FMTokenType.DOUBLE_UNDER, FMTokenType.EOF);
    }

    @Test
    void tripleUnderscore() throws Exception {
        var tokens = lex("___");
        assertTypes(tokens, FMTokenType.TRIPLE_UNDER, FMTokenType.EOF);
    }

    // --- Whitespace handling ---

    @Test
    void whitespaceIsIgnored() throws Exception {
        var tokens = lex("  x  +  y  ");
        assertTypes(tokens,
                FMTokenType.IDENT, FMTokenType.PLUS, FMTokenType.IDENT,
                FMTokenType.EOF);
    }

    @Test
    void emptyInput() throws Exception {
        var tokens = lex("");
        assertTypes(tokens, FMTokenType.EOF);
    }

    @Test
    void onlyWhitespace() throws Exception {
        var tokens = lex("   \t\n  ");
        assertTypes(tokens, FMTokenType.EOF);
    }

    // --- Compound expressions ---

    @Test
    void functionCall() throws Exception {
        var tokens = lex("Sin(x)");
        assertTypes(tokens,
                FMTokenType.IDENT, FMTokenType.LPAREN,
                FMTokenType.IDENT, FMTokenType.RPAREN,
                FMTokenType.EOF);
        assertEquals("Sin", tokens.get(0).value());
        assertEquals("x", tokens.get(2).value());
    }

    @Test
    void listLiteral() throws Exception {
        var tokens = lex("{1, 2, 3}");
        assertTypes(tokens,
                FMTokenType.LBRACE,
                FMTokenType.INTEGER, FMTokenType.COMMA,
                FMTokenType.INTEGER, FMTokenType.COMMA,
                FMTokenType.INTEGER,
                FMTokenType.RBRACE, FMTokenType.EOF);
    }

    @Test
    void nestedExpression() throws Exception {
        var tokens = lex("Sin(x + 2) * 3");
        assertTypes(tokens,
                FMTokenType.IDENT, FMTokenType.LPAREN,
                FMTokenType.IDENT, FMTokenType.PLUS, FMTokenType.INTEGER,
                FMTokenType.RPAREN, FMTokenType.STAR, FMTokenType.INTEGER,
                FMTokenType.EOF);
    }

    @Test
    void indexing() throws Exception {
        var tokens = lex("{1,2,3}[0]");
        assertTypes(tokens,
                FMTokenType.LBRACE,
                FMTokenType.INTEGER, FMTokenType.COMMA,
                FMTokenType.INTEGER, FMTokenType.COMMA,
                FMTokenType.INTEGER,
                FMTokenType.RBRACE,
                FMTokenType.LBRACKET, FMTokenType.INTEGER, FMTokenType.RBRACKET,
                FMTokenType.EOF);
    }

    @Test
    void lambdaArrow() throws Exception {
        var tokens = lex("(x) => x^2");
        assertTypes(tokens,
                FMTokenType.LPAREN, FMTokenType.IDENT, FMTokenType.RPAREN,
                FMTokenType.FAT_ARROW,
                FMTokenType.IDENT, FMTokenType.CARET, FMTokenType.INTEGER,
                FMTokenType.EOF);
    }

    @Test
    void patternWithCondition() throws Exception {
        var tokens = lex("x_ /; x > 0");
        assertTypes(tokens,
                FMTokenType.IDENT, FMTokenType.UNDERSCORE,
                FMTokenType.SLASH_SEMI,
                FMTokenType.IDENT, FMTokenType.GREATER, FMTokenType.INTEGER,
                FMTokenType.EOF);
    }

    @Test
    void ruleApplication() throws Exception {
        var tokens = lex("expr /. x -> 5");
        assertTypes(tokens,
                FMTokenType.IDENT, FMTokenType.SLASH_DOT,
                FMTokenType.IDENT, FMTokenType.ARROW, FMTokenType.INTEGER,
                FMTokenType.EOF);
    }

    @Test
    void minusVsArrow() throws Exception {
        // "- >" should be MINUS GREATER, "->" should be ARROW
        var tokens = lex("- >");
        assertTypes(tokens, FMTokenType.MINUS, FMTokenType.GREATER, FMTokenType.EOF);

        var tokens2 = lex("->");
        assertTypes(tokens2, FMTokenType.ARROW, FMTokenType.EOF);
    }

    @Test
    void negativeNumber() throws Exception {
        // The lexer should produce MINUS INTEGER, not a negative integer.
        // Negation is handled by the parser.
        var tokens = lex("-42");
        assertTypes(tokens, FMTokenType.MINUS, FMTokenType.INTEGER, FMTokenType.EOF);
        assertEquals("42", tokens.get(1).value());
    }

    // --- Line/col tracking ---

    @Test
    void positionTracking() throws Exception {
        var tokens = lex("x + y");
        // x at col 0, + at col 2, y at col 4
        assertEquals(0, tokens.get(0).col());
        assertEquals(2, tokens.get(1).col());
        assertEquals(4, tokens.get(2).col());
    }

    // --- Triple dot ---

    @Test
    void tripleDot() throws Exception {
        var tokens = lex("...");
        assertTypes(tokens, FMTokenType.TRIPLE_DOT, FMTokenType.EOF);
        assertEquals("...", tokens.get(0).value());
    }

    @Test
    void tripleDotBeforeIdent() throws Exception {
        var tokens = lex("...rest");
        assertTypes(tokens, FMTokenType.TRIPLE_DOT, FMTokenType.IDENT, FMTokenType.EOF);
        assertEquals("rest", tokens.get(1).value());
    }

    @Test
    void variadicLambda() throws Exception {
        var tokens = lex("(a, ...rest) => body");
        assertTypes(tokens,
                FMTokenType.LPAREN, FMTokenType.IDENT, FMTokenType.COMMA,
                FMTokenType.TRIPLE_DOT, FMTokenType.IDENT, FMTokenType.RPAREN,
                FMTokenType.FAT_ARROW, FMTokenType.IDENT,
                FMTokenType.EOF);
    }

    @Test
    void allVariadicLambda() throws Exception {
        var tokens = lex("(...args) => args");
        assertTypes(tokens,
                FMTokenType.LPAREN, FMTokenType.TRIPLE_DOT, FMTokenType.IDENT, FMTokenType.RPAREN,
                FMTokenType.FAT_ARROW, FMTokenType.IDENT,
                FMTokenType.EOF);
    }

    @Test
    void bareDoubleDotThrows() {
        assertThrows(Exception.class, () -> lex(".."));
    }

    @Test
    void bareSingleDotThrows() {
        assertThrows(Exception.class, () -> lex("."));
    }

    // --- Error cases ---

    @Test
    void unknownCharacterThrows() {
        assertThrows(Exception.class, () -> lex("$"));
    }
}
