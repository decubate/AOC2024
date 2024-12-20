package day11

import getLines

fun main() {
    val solver = Dec11()
    solver.partOne()
}

class Dec11 {
    fun partOne() {
        var stones = getLines("day11")[0].split(" ")
        repeat(25) {
            stones = stones.flatMap { it.blink() }
        }
        println(stones.size)
    }
}

/*
If the stone is engraved with the number 0, it is replaced by a stone engraved with the number 1.
If the stone is engraved with a number that has an even number of digits, it is replaced by two stones. The left half of the digits are engraved on the new left stone, and the right half of the digits are engraved on the new right stone. (The new numbers don't keep extra leading zeroes: 1000 would become stones 10 and 0.)
If none of the other rules apply, the stone is replaced by a new stone; the old stone's number multiplied by 2024 is engraved on the new stone.
 */

private fun String.blink(): List<String> {
    if (this.toLong() == 0L) return listOf("1")
    if (this.length % 2 == 0) return listOf(this.take(this.length / 2), this.takeLast(this.length / 2).toLong().toString())
    return listOf((this.toLong()*2024L).toString())
}
