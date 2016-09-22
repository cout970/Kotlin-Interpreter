package com.cout970.ps.lexer

import com.cout970.ps.components.CompFun
import com.cout970.ps.tokenizer.ITokenStream
import com.cout970.ps.tokenizer.TokenType

/**
 * Created by cout970 on 2016/09/22.
 */
object ParserFunction {

    fun parse(stream: ITokenStream): CompFun {
        println(stream.readToken().text)//fun
        println(stream.readToken().text)//name
        println(stream.readToken().text)//(
        println(stream.readToken().text)//)
        println(stream.readToken().text)//{
        var look = stream.readToken()
        while(look.type != TokenType.PAREN_CLOSE){
            look = stream.readToken()
        }
        return CompFun("")
    }
}