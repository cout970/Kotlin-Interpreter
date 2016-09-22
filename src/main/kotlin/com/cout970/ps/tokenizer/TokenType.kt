package com.cout970.ps.tokenizer

/**
 * Created by cout970 on 2016/09/17.
 */
enum class TokenType() {
    IDENTIFIER,
    NUMBER,
    TRUE,
    FALSE,
    STRING,
    CHAR,
    VAR,
    VAL,
    CLASS,
    ATTRIBUTE_PUBLIC,
    ATTRIBUTE_PRIVATE,
    ATTRIBUTE_PROTECTED,
    ATTRIBUTE_INTERNAL,
    ENUM,
    FUN,
    IF,
    ELSE,
    WHILE,
    FOR,
    WHEN,
    WHERE,
    INLINE,
    NOINLINE,
    PACKAGE,
    IMPORT,
    //no identifier token
    DOT,
    COMMA,
    COLON,
    PAREN_OPEN,
    PAREN_CLOSE,
    BRACKET_OPEN,
    BRACKET_CLOSE,
    CURLY_BRACKET_OPEN,
    CURLY_BRACKET_CLOSE,
    ASSIGN,
    EQUALS,
    NOT_EQUALS,
    IDENTITY_EQUALS,
    IDENTITY_NOT_EQUALS,
    MORE,
    LESS,
    MORE_EQUAL,
    LESS_EQUAL,
    PLUS,
    ATOMIC_INCREMENT,
    ATOMIC_DECREMENT,
    INCREMENT_ASSIGN,
    DECREMENT_ASSIGN,
    TIMES_ASSIGN,
    DIV_ASSIGN,
    MOD_ASSIGN,
    ARROW,
    MINUS,
    STAR,
    DIV,
    MOD,
    AT,
    OR,
    AND,
    LINE_FEED,
    SEMICOLON,
    QUESTION_MARK,
    UNKNOWN;

    companion object {
        val IDENTIFIER_TOKENS = mapOf<String, TokenType>(
                "true" to TRUE,
                "false" to FALSE,
                "var" to VAR,
                "val" to VAL,
                "class" to CLASS,
                "public" to ATTRIBUTE_PUBLIC,
                "private" to ATTRIBUTE_PRIVATE,
                "protected" to ATTRIBUTE_PROTECTED,
                "internal" to ATTRIBUTE_INTERNAL,
                "package" to PACKAGE,
                "import" to IMPORT,
                "enum" to ENUM,
                "fun" to FUN,
                "if" to IF,
                "else" to ELSE,
                "while" to WHILE,
                "for" to FOR,
                "when" to WHEN,
                "where" to WHERE,
                "inline" to INLINE,
                "noinline" to NOINLINE
        )

        val SPECIAL_TOKENS = mapOf<String, TokenType>(
                "." to DOT,
                "," to COMMA,
                ":" to COLON,
                ";" to SEMICOLON,
                "(" to PAREN_OPEN,
                ")" to PAREN_CLOSE,
                "[" to BRACKET_OPEN,
                "]" to BRACKET_CLOSE,
                "{" to CURLY_BRACKET_OPEN,
                "}" to CURLY_BRACKET_CLOSE,
                ">" to MORE,
                "<" to LESS,
                "+" to PLUS,
                "-" to MINUS,
                "*" to STAR,
                "/" to DIV,
                "%" to MOD,
                "@" to AT,
                "?" to QUESTION_MARK
        )
    }
}