package com.cout970.ps.input

import java.io.File

/**
 * Created by cout970 on 2016/09/19.
 */
class FileCharInputStream(val file: File) : CharInputSteam {

    private var line = -1
    private var column = 0
    private val cache: List<String>

    init {
        cache = file.readLines().map { it + '\n' }
        if (cache.size == 0) column = -1
    }

    override fun read(): Char {
        if (column == -1) {
            return (-1).toChar()
        }
        line++
        while (column != -1 && cache[column].length <= line) {
            line = 0
            column++
            if (column >= cache.size) {
                column = -1
            }
        }
        if (column == -1) {
            return (-1).toChar()
        }
        val tmp = cache[column][line]
        return tmp
    }

    override fun getFile(): String = file.name

    override fun getLine(): Int = line

    override fun getColumn(): Int = column
}