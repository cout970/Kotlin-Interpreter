package com.cout970.ps

import com.cout970.ps.input.FileCharInputStream
import com.cout970.ps.tokenizer.Token
import com.cout970.ps.tokenizer.TokenType
import com.cout970.ps.tokenizer.Tokenizer
import java.io.File

/**
 * Created by cout970 on 2016/09/22.
 */

fun main(args: Array<String>) {

    val stream = FileCharInputStream(File("./src/main/kotlin/test.ls"))
    val tokenizer = Tokenizer(stream)
    var indent = 0

    var token: Token
    var look: Token = tokenizer.readToken()
    do {
        token = look
        look = tokenizer.readToken()

        if (token.type == TokenType.LINE_FEED) {
            println()
            if (look.type == TokenType.CURLY_BRACKET_CLOSE) {
                repeat(indent - 1) {
                    print("\t")
                }
            } else {
                repeat(indent) {
                    print("\t")
                }
            }
        } else if (token.type == TokenType.STRING) {
            print("\"${token.text}\" ")
        } else if (token.type == TokenType.CHAR) {
            val code = "\\u"+"%4x".format(token.text[0].toShort().toInt()).replace(" ", "0")
            print("\'$code\' ")
        } else if (token.type == TokenType.CURLY_BRACKET_OPEN) {
            indent++
            print(token.text + " ")
        } else if (token.type == TokenType.CURLY_BRACKET_CLOSE) {
            indent--
            print("} ")
        } else {
            print(token.text + " ")
        }
    } while (token.type != TokenType.UNKNOWN)
    println()
    System.out.flush()
    Thread.sleep(500)
}