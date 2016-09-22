package com.cout970.ps.lexer

import com.cout970.ps.components.CompImport
import com.cout970.ps.tokenizer.ITokenStream
import com.cout970.ps.tokenizer.TokenType

/**
 * Created by cout970 on 2016/09/20.
 */
object ParserImport {

    fun parse(tk: ITokenStream): CompImport {
        var look = tk.readToken()
        look.expect(TokenType.IMPORT, "import keyword")
        look = tk.readToken()
        look.expect(TokenType.IDENTIFIER, "import path")
        var path = look.text
        look = tk.readToken()
        while (look.type == TokenType.DOT) {
            look = tk.readToken()
            look.expect(TokenType.IDENTIFIER, "import path")
            path += "." + look.text
            look = tk.readToken()
        }
        return CompImport(path)
    }
}