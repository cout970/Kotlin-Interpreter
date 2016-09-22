package com.cout970.ps.input

/**
 * Created by cout970 on 2016/09/19.
 */
interface CharInputSteam {

    fun read(): Char

    fun getFile(): String
    fun getLine(): Int
    fun getColumn(): Int
}