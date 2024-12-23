package day22

import getLines

fun main() {
    val solver = Dec22()
    solver.partOne()
}

class Dec22 {
    fun partOne() {
        val initialNumbers = getLines("day22").map { it.toLong() }
        println(initialNumbers.sumOf { toSecretNumber(it, 2000) })
    }
}

private fun toSecretNumber(initial: Long, iterations: Int): Long {
    var secretNumber = initial
    for (i in 0..< iterations) {
        val mul = secretNumber * 64
        secretNumber = mix(secretNumber, mul)
        secretNumber = prune(secretNumber)
        val div = (secretNumber.toFloat() / 32f).toInt()
        secretNumber = mix(secretNumber, div.toLong())
        secretNumber = prune(secretNumber)
        val mul2 = secretNumber * 2048
        secretNumber = mix(secretNumber, mul2)
        secretNumber = prune(secretNumber)
    }
    return secretNumber
}

fun prune(secretNumber: Long): Long {
    return secretNumber % 16777216
}

fun mix(secretNumber: Long, value: Long): Long {
    return value xor secretNumber
}
