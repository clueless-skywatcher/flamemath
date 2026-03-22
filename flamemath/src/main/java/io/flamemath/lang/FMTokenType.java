package io.flamemath.lang;

public enum FMTokenType {
    // Literals
    INTEGER,
    REAL,
    STRING,

    // Identifiers & symbols
    IDENT,

    // Operators — arithmetic
    PLUS,           // +
    MINUS,          // -
    STAR,           // *
    SLASH,          // /
    CARET,          // ^

    // Operators — comparison
    EQUAL_EQUAL,    // ==
    BANG_EQUAL,     // !=
    LESS,           // <
    LESS_EQUAL,     // <=
    GREATER,        // >
    GREATER_EQUAL,  // >=

    // Operators — logical
    AMP_AMP,        // &&
    PIPE_PIPE,      // ||
    BANG,           // !

    // Assignment & rules
    EQUAL,          // =
    ARROW,          // ->
    COLON_GREATER,  // :>
    SLASH_DOT,      // /.

    // Pipe
    SLASH_SLASH,    // //
    PIPE_GREATER,   // |>

    // Delimiters
    LPAREN,         // (
    RPAREN,         // )
    LBRACE,         // {
    RBRACE,         // }
    LBRACKET,       // [
    RBRACKET,       // ]
    COMMA,          // ,
    COLON,          // :
    SEMICOLON,      // ;

    // Lambda
    FAT_ARROW,      // =>
    AT,             // @

    // Pattern matching
    UNDERSCORE,     // _
    DOUBLE_UNDER,   // __
    TRIPLE_UNDER,   // ___
    SLASH_SEMI,     // /;  (condition)

    TRIPLE_DOT,     //... (variadic arguments)

    // Special
    EOF
}
