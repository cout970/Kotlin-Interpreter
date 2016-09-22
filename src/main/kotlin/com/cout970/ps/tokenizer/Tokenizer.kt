package com.cout970.ps.tokenizer

import com.cout970.ps.input.CharInputSteam
import java.util.regex.Pattern

/**
 * Created by cout970 on 2016/09/19.
 */
class Tokenizer(val stream: CharInputSteam) {

    companion object {
        val PATTERN_NUMBER = Pattern.compile("[0-9]+")
        val PATTERN_IDENTIFIER_FIRST = Pattern.compile("[a-zA-Z]")
        val PATTERN_IDENTIFIER = Pattern.compile("[a-zA-Z0-9_]")
    }

    var look = stream.read()
    var line = 0
    var column = 0
    var file = ""

    //902b345a414c
    private fun read() {
        look = stream.read()
    }

    private fun reset() {
        line = stream.getLine()
        column = stream.getColumn()
        file = stream.getFile()
    }

    fun readToken(): Token {
        while (look == ' ' || look == '\t') {
            read()
        }
        reset()
        if (look == '\n') {
            read()
            while (look == '\n') {
                read()
            }
            return Token("\n", TokenType.LINE_FEED, file, line, column)
        } else if (PATTERN_NUMBER.matcher(look.toString()).matches()) {
            var acum = ""
            do {
                acum += look
                read()
            } while (PATTERN_NUMBER.matcher(look.toString()).matches())

            return Token(acum, TokenType.NUMBER, file, line, column)
        } else if (PATTERN_IDENTIFIER_FIRST.matcher(look.toString()).matches()) {
            var acum = ""
            do {
                acum += look
                read()
            } while (PATTERN_IDENTIFIER.matcher(look.toString()).matches())

            for ((key, value) in TokenType.IDENTIFIER_TOKENS) {
                if (key == acum) {
                    return Token(acum, value, file, line, column)
                }
            }
            return Token(acum, TokenType.IDENTIFIER, file, line, column)
        } else if (look == '\"') {
            var acum = ""
            read()
            var last = ' '
            while (look != '\"' || last == '\\') {
                acum += look
                last = look
                read()
            }
            read()
            return Token(acum, TokenType.STRING, file, line, column)
        } else if (look == '/') {
            read()
            if (look == '/') {
                read()
                while (look != '\n') {
                    read()
                }
                return readToken()
            } else if (look == '*') {
                read()
                while (true) {
                    if (look == '*') {
                        read()
                        if (look == '/') {
                            read()
                            break
                        }
                    } else {
                        read()
                    }
                }
                while (look == '\n' || look == ' ') {
                    read()
                }
                return readToken()
            } else {
                return Token("/", TokenType.DIV, file, line, column)
            }
        } else if (look == '\'') {
            read()
            var temp = look.toString()
            if (look == '\\') {
                read()
                temp = when (look) {
                    't' -> {
                        read(); '\t'.toString()
                    }
                    'n' -> {
                        read(); '\n'.toString()
                    }
                    'r' -> {
                        read(); '\r'.toString()
                    }
                    '0' -> {
                        read(); 0.toString()
                    }
                    'u' -> {
                        var acum = "u"
                        read()
                        for (i in 0 until 4) {
                            acum += look
                            read()
                        }
                        acum
                    }
                    else -> {
                        val t = look.toString()
                        read(); t
                    } //end of text -> error
                }
            } else {
                read()
            }
            if (look == '\'') {
                read()
                return Token(temp, TokenType.CHAR, file, line, column)
            }
            return Token(temp + look, TokenType.UNKNOWN, file, line, column)
        } else {
            val temp = look.toString()
            read()
            var token: Token? = null
            for ((key, value) in TokenType.SPECIAL_TOKENS.filter { it.key.length == 1 }) {
                if (temp == key) {
                    token = Token(temp.toString(), value, file, line, column)
                    break
                }
            }
            if (temp == "!" && look == '=') {
                read()
                if (look == '=') {
                    read()
                    return Token("!=", TokenType.IDENTITY_NOT_EQUALS, file, line, column)
                }
                return Token("!=", TokenType.NOT_EQUALS, file, line, column)
            }

            if (temp == "=") {
                if (look == '=') {
                    read()
                    if (look == '=') {
                        read()
                        return Token("===", TokenType.IDENTITY_EQUALS, file, line, column)
                    }
                    return Token("==", TokenType.NOT_EQUALS, file, line, column)
                } else {
                    return Token("=", TokenType.ASSIGN, file, line, column)
                }
            }

            if (temp == "+" && look == '+') {
                read()
                return Token("++", TokenType.ATOMIC_INCREMENT, file, line, column)
            }

            if (temp == "-" && look == '-') {
                read()
                return Token("--", TokenType.ATOMIC_DECREMENT, file, line, column)
            }

            if (temp == "+" && look == '=') {
                read()
                return Token("+=", TokenType.INCREMENT_ASSIGN, file, line, column)
            }

            if (temp == "-" && look == '=') {
                read()
                return Token("-=", TokenType.DECREMENT_ASSIGN, file, line, column)
            }

            if (temp == "*" && look == '=') {
                read()
                return Token("*=", TokenType.TIMES_ASSIGN, file, line, column)
            }

            if (temp == "/" && look == '=') {
                read()
                return Token("/=", TokenType.DIV_ASSIGN, file, line, column)
            }

            if (temp == "%" && look == '=') {
                read()
                return Token("%=", TokenType.MOD_ASSIGN, file, line, column)
            }

            if (temp == ">" && look == '=') {
                read()
                return Token(">=", TokenType.MORE_EQUAL, file, line, column)
            }

            if (temp == "-" && look == '>') {
                read()
                return Token("->", TokenType.ARROW, file, line, column)
            }

            if (temp == "<" && look == '=') {
                read()
                return Token("<=", TokenType.MORE_EQUAL, file, line, column)
            }

            if (temp == "|" && look == '|') {
                read()
                return Token("||", TokenType.OR, file, line, column)
            }

            if (temp == "&" && look == '&') {
                read()
                return Token("&&", TokenType.AND, file, line, column)
            }
            if (token != null) return token
            return Token(temp + look, TokenType.UNKNOWN, file, line, column)
        }
    }
}