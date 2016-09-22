package com.cout970.ps.util

/**
 * Created by cout970 on 2016/09/17.
 */
class AutomataFinitoDeterminista<T>(val states: Set<T>, val alphabet: Set<Char>, val initialValue: T, val function: (T, Char) -> T, val finalValues: Set<T>) {

    var currentState = initialValue

    init {
        if (initialValue !in states) throw IllegalArgumentException("The initial state: '$initialValue', is not in the valid states: $states")
        if (!finalValues.all { it in states }) throw IllegalArgumentException("One of the final states: '$finalValues', is not in the valid states: $states")
    }

    fun process(c: Char) {
        val n = function(currentState, c)
        if (n !in states) throw IllegalStateException("The current state: '$n', is not in the valid states: $states")
        currentState = n
    }

    fun process(str: String) {
        for (i in str) {
            process(i)
        }
    }

    fun isAtEnd() = currentState in finalValues
}