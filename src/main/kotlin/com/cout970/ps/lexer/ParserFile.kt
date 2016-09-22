package com.cout970.ps.lexer

import com.cout970.ps.components.CompClass
import com.cout970.ps.components.CompFun
import com.cout970.ps.components.CompImport
import com.cout970.ps.components.CompPackage
import com.cout970.ps.input.FileCharInputStream
import com.cout970.ps.tokenizer.TokenType
import com.cout970.ps.tokenizer.Tokenizer
import java.io.File

/**
 * Created by cout970 on 2016/09/22.
 */
class ParserFile(file: File) {

    val tokenizer = Tokenizer(FileCharInputStream(file))
    var compPackage: CompPackage? = null
    val compImports = mutableListOf<CompImport>()
    val compClasses = mutableListOf<CompClass>()
    val compFunctions = mutableListOf<CompFun>()

    fun parse() {
        var look = tokenizer.readToken()

        while (look.type != TokenType.UNKNOWN) {
            if (look.type == TokenType.PACKAGE) {
                if (compPackage == null) {
                    compPackage = ParserPackage.parse(look, tokenizer)
                } else {
                    throw DuplicatedElementException("Duplicated package directive at line: ${look.line}, column: ${look.column}, in file: ${look.file}")
                }
            } else if (look.type == TokenType.IMPORT) {
                compImports.add(ParserImport.parse(look, tokenizer))
            } else if (look.type == TokenType.CLASS) {
                compClasses.add(ParserClass.parse(look, tokenizer))
            } else if(look.type != TokenType.LINE_FEED && look.type != TokenType.SEMICOLON){
                throw UnexpectedToken(look)
            }
            look = tokenizer.readToken()
        }
    }

}