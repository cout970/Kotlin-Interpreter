package com.cout970.ps

import com.cout970.ps.lexer.ParserFile
import java.io.File

/**
 * Created by cout970 on 2016/09/17.
 */

fun main(args: Array<String>) {

    val parser = ParserFile(File("./src/main/kotlin/test.ls"))
    parser.parse()
    println()
    System.out.flush()
    Thread.sleep(500)
}
