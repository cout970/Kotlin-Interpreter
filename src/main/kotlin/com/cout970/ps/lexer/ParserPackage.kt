package com.cout970.ps.lexer

import com.cout970.ps.components.CompPackage
import com.cout970.ps.tokenizer.Token
import com.cout970.ps.tokenizer.TokenType
import com.cout970.ps.tokenizer.Tokenizer

/**
 * Created by cout970 on 2016/09/20.
 */
object ParserPackage {

    fun parse(first: Token, tk: Tokenizer): CompPackage {
        var look = first
        look.expect(TokenType.PACKAGE, "package keyword")
        look = tk.readToken()
        look.expect(TokenType.IDENTIFIER, "package path")
        var path = look.text
        look = tk.readToken()
        while (look.type == TokenType.DOT) {
            look = tk.readToken()
            look.expect(TokenType.IDENTIFIER, "package path")
            path += "." + look.text
            look = tk.readToken()
        }
        return CompPackage(path)
    }
}