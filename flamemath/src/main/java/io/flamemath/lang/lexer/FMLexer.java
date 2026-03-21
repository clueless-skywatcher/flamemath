package io.flamemath.lang.lexer;

import java.util.ArrayList;
import java.util.List;

import io.flamemath.lang.FMToken;
import io.flamemath.lang.FMTokenType;

public class FMLexer {
    private final String code;
    private int pos;
    private int line;
    private int col;

    public FMLexer(String code) {
        this.code = code;
        this.pos = 0;
        this.line = 1;
        this.col = 0;
    }

    public List<FMToken> lex() throws Exception {
        List<FMToken> tokens = new ArrayList<>();

        while (pos < code.length()) {
            char c = peek();

            if (Character.isWhitespace(c)) {
                advance();
                continue;
            }

            if (c == '.' || Character.isDigit(c)) {
                // Leading dot: only numeric if followed by a digit
                if (c == '.' && (pos + 1 >= code.length() || !Character.isDigit(code.charAt(pos + 1)))) {
                    // Not a number — fall through to slash-dot etc. won't reach here
                    // since '.' alone is not a valid token in our language.
                    throw new Exception("Unexpected '.' at " + line + ":" + col);
                }
                tokens.add(lexNumeric());
                continue;
            }

            if (Character.isLetter(c)) {
                tokens.add(lexIdent());
                continue;
            }

            if (c == '"') {
                tokens.add(lexString());
                continue;
            }

            if (c == '_') {
                tokens.add(lexUnderscore());
                continue;
            }

            tokens.add(lexOperatorOrDelimiter());
        }

        tokens.add(new FMToken(FMTokenType.EOF, "", line, col));
        return tokens;
    }

    // --- Character helpers ---

    private char peek() {
        return code.charAt(pos);
    }

    // private char peek(int offset) {
    //     int idx = pos + offset;
    //     return idx < code.length() ? code.charAt(idx) : '\0';
    // }

    private char advance() {
        char c = code.charAt(pos);
        pos++;
        if (c == '\n') {
            line++;
            col = 0;
        } else {
            col++;
        }
        return c;
    }

    // --- Numeric: integers, reals, scientific notation ---

    private FMToken lexNumeric() throws Exception {
        int startCol = col;
        int startLine = line;
        StringBuilder sb = new StringBuilder();
        boolean hasDecimal = false;
        boolean hasExponent = false;

        // Integer or real part
        while (pos < code.length()) {
            char c = peek();
            if (Character.isDigit(c)) {
                sb.append(advance());
            } else if (c == '.') {
                if (hasDecimal || hasExponent) {
                    throw new Exception("Unexpected '.' at " + line + ":" + col);
                }
                hasDecimal = true;
                sb.append(advance());
            } else if (c == 'e' || c == 'E') {
                if (hasExponent) {
                    throw new Exception("Unexpected '" + c + "' at " + line + ":" + col);
                }
                hasExponent = true;
                hasDecimal = true; // scientific notation is always real
                sb.append(advance());
                // Optional sign after exponent
                if (pos < code.length() && (peek() == '+' || peek() == '-')) {
                    sb.append(advance());
                }
            } else {
                break;
            }
        }

        FMTokenType type = hasDecimal ? FMTokenType.REAL : FMTokenType.INTEGER;
        return new FMToken(type, sb.toString(), startLine, startCol);
    }

    // --- Identifiers ---

    private FMToken lexIdent() {
        int startCol = col;
        int startLine = line;
        StringBuilder sb = new StringBuilder();

        while (pos < code.length()) {
            char c = peek();
            if (Character.isLetterOrDigit(c)) {
                sb.append(advance());
            } else {
                break;
            }
        }

        return new FMToken(FMTokenType.IDENT, sb.toString(), startLine, startCol);
    }

    // --- String literals ---

    private FMToken lexString() throws Exception {
        int startCol = col;
        int startLine = line;
        advance(); // consume opening "
        StringBuilder sb = new StringBuilder();

        while (pos < code.length()) {
            char c = advance();
            if (c == '"') {
                return new FMToken(FMTokenType.STRING, sb.toString(), startLine, startCol);
            }
            if (c == '\\' && pos < code.length()) {
                char escaped = advance();
                switch (escaped) {
                    case 'n' -> sb.append('\n');
                    case 't' -> sb.append('\t');
                    case '\\' -> sb.append('\\');
                    case '"' -> sb.append('"');
                    default -> { sb.append('\\'); sb.append(escaped); }
                }
            } else {
                sb.append(c);
            }
        }

        throw new Exception("Unterminated string starting at " + startLine + ":" + startCol);
    }

    // --- Underscores: _, __, ___ ---

    private FMToken lexUnderscore() {
        int startCol = col;
        int startLine = line;
        advance(); // first _
        if (pos < code.length() && peek() == '_') {
            advance(); // second _
            if (pos < code.length() && peek() == '_') {
                advance(); // third _
                return new FMToken(FMTokenType.TRIPLE_UNDER, "___", startLine, startCol);
            }
            return new FMToken(FMTokenType.DOUBLE_UNDER, "__", startLine, startCol);
        }
        return new FMToken(FMTokenType.UNDERSCORE, "_", startLine, startCol);
    }

    // --- Operators and delimiters ---

    private FMToken lexOperatorOrDelimiter() throws Exception {
        int startCol = col;
        int startLine = line;
        char c = advance();

        switch (c) {
            case '+' -> { return new FMToken(FMTokenType.PLUS, "+", startLine, startCol); }
            case '*' -> { return new FMToken(FMTokenType.STAR, "*", startLine, startCol); }
            case '^' -> { return new FMToken(FMTokenType.CARET, "^", startLine, startCol); }
            case '(' -> { return new FMToken(FMTokenType.LPAREN, "(", startLine, startCol); }
            case ')' -> { return new FMToken(FMTokenType.RPAREN, ")", startLine, startCol); }
            case '{' -> { return new FMToken(FMTokenType.LBRACE, "{", startLine, startCol); }
            case '}' -> { return new FMToken(FMTokenType.RBRACE, "}", startLine, startCol); }
            case '[' -> { return new FMToken(FMTokenType.LBRACKET, "[", startLine, startCol); }
            case ']' -> { return new FMToken(FMTokenType.RBRACKET, "]", startLine, startCol); }
            case ',' -> { return new FMToken(FMTokenType.COMMA, ",", startLine, startCol); }
            case ';' -> { return new FMToken(FMTokenType.SEMICOLON, ";", startLine, startCol); }
            case '@' -> { return new FMToken(FMTokenType.AT, "@", startLine, startCol); }

            case '-' -> {
                if (pos < code.length() && peek() == '>') {
                    advance();
                    return new FMToken(FMTokenType.ARROW, "->", startLine, startCol);
                }
                return new FMToken(FMTokenType.MINUS, "-", startLine, startCol);
            }

            case '=' -> {
                if (pos < code.length() && peek() == '=') {
                    advance();
                    return new FMToken(FMTokenType.EQUAL_EQUAL, "==", startLine, startCol);
                }
                if (pos < code.length() && peek() == '>') {
                    advance();
                    return new FMToken(FMTokenType.FAT_ARROW, "=>", startLine, startCol);
                }
                return new FMToken(FMTokenType.EQUAL, "=", startLine, startCol);
            }

            case '!' -> {
                if (pos < code.length() && peek() == '=') {
                    advance();
                    return new FMToken(FMTokenType.BANG_EQUAL, "!=", startLine, startCol);
                }
                return new FMToken(FMTokenType.BANG, "!", startLine, startCol);
            }

            case '<' -> {
                if (pos < code.length() && peek() == '=') {
                    advance();
                    return new FMToken(FMTokenType.LESS_EQUAL, "<=", startLine, startCol);
                }
                return new FMToken(FMTokenType.LESS, "<", startLine, startCol);
            }

            case '>' -> {
                if (pos < code.length() && peek() == '=') {
                    advance();
                    return new FMToken(FMTokenType.GREATER_EQUAL, ">=", startLine, startCol);
                }
                return new FMToken(FMTokenType.GREATER, ">", startLine, startCol);
            }

            case '&' -> {
                if (pos < code.length() && peek() == '&') {
                    advance();
                    return new FMToken(FMTokenType.AMP_AMP, "&&", startLine, startCol);
                }
                throw new Exception("Unexpected '&' at " + startLine + ":" + startCol + " (did you mean '&&'?)");
            }

            case '|' -> {
                if (pos < code.length() && peek() == '|') {
                    advance();
                    return new FMToken(FMTokenType.PIPE_PIPE, "||", startLine, startCol);
                }
                if (pos < code.length() && peek() == '>') {
                    advance();
                    return new FMToken(FMTokenType.PIPE_GREATER, "|>", startLine, startCol);
                }
                throw new Exception("Unexpected '|' at " + startLine + ":" + startCol + " (did you mean '||' or '|>'?)");
            }

            case '/' -> {
                if (pos < code.length() && peek() == '.') {
                    advance();
                    return new FMToken(FMTokenType.SLASH_DOT, "/.", startLine, startCol);
                }
                if (pos < code.length() && peek() == '/') {
                    advance();
                    return new FMToken(FMTokenType.SLASH_SLASH, "//", startLine, startCol);
                }
                if (pos < code.length() && peek() == ';') {
                    advance();
                    return new FMToken(FMTokenType.SLASH_SEMI, "/;", startLine, startCol);
                }
                return new FMToken(FMTokenType.SLASH, "/", startLine, startCol);
            }

            case ':' -> {
                if (pos < code.length() && peek() == '>') {
                    advance();
                    return new FMToken(FMTokenType.COLON_GREATER, ":>", startLine, startCol);
                }
                return new FMToken(FMTokenType.COLON, ":", startLine, startCol);
            }

            default -> throw new Exception("Unexpected character '" + c + "' at " + startLine + ":" + startCol);
        }
    }
}
