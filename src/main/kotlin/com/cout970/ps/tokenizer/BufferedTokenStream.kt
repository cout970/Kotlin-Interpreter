package com.cout970.ps.tokenizer

import java.util.*

/**
 * Created by cout970 on 2016/09/22.
 */
class BufferedTokenStream(list_: List<Token>, overflow_: ITokenStream) : ITokenStream {

    val overflow = overflow_
    var list: LinkedList<Token> = LinkedList(list_)

    override fun readToken(): Token {
        if(list.isEmpty()) return overflow.readToken()
        return list.poll()
    }
}