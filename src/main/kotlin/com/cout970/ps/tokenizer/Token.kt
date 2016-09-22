package com.cout970.ps.tokenizer

import com.cout970.ps.lexer.UnexpectedToken

/**
 * Created by cout970 on 2016/09/19.
 */
data class Token(val text: String, val type: TokenType, val file: String, val line: Int, val column: Int) {

    fun expect(type: TokenType, expect: String) {
        if (this.type != type) throw UnexpectedToken(this, expect)
    }
}