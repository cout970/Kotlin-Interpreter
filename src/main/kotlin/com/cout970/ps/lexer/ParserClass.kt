package com.cout970.ps.lexer

import com.cout970.ps.components.AccessModifier
import com.cout970.ps.components.CompClass
import com.cout970.ps.tokenizer.ITokenStream
import com.cout970.ps.tokenizer.TokenType

/**
 * Created by cout970 on 2016/09/22.
 */
object ParserClass {

    fun parse(tk: ITokenStream): CompClass {
        var modifier = AccessModifier.PUBLIC
        var token = tk.readToken()
        val name: String

        if (AccessModifier.isModifier(token.type)) {
            modifier = AccessModifier.getModifier(token.type)
            token = tk.readToken()
        }

        token.expect(TokenType.CLASS, "class keyword ")
        token = tk.readToken()

        if (token.type == TokenType.IDENTIFIER) {
            name = token.text
            token = tk.readToken()
        } else {
            throw UnexpectedToken(token, "class name")
        }

        token.expect(TokenType.PAREN_OPEN, "Open paren '(' ")
        token = tk.readToken()
        token.expect(TokenType.PAREN_CLOSE, "Close paren ')' ")

        return CompClass(modifier, name)
    }
}