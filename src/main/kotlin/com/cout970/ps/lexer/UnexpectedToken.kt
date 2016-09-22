package com.cout970.ps.lexer

import com.cout970.ps.tokenizer.Token

/**
 * Created by cout970 on 2016/09/20.
 */
class UnexpectedToken(tk: Token, expected: String? = null) : RuntimeException(
        if (expected == null)
            "Unexpected token read: '${tk.text}', type: ${tk.type}, at line: ${tk.line}, column: ${tk.column}, in file: ${tk.file} "
        else
            "Expected '$expected' but found '${tk.text}', type: ${tk.type}, at line: ${tk.line}, column: ${tk.column}, in file: ${tk.file}"
)