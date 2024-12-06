package day02

import getLines
import kotlin.math.absoluteValue

fun main() {
    val solver = Dec02()
    solver.partTwo()
}

class Dec02 {
    fun partOne() {
        val lines = getLines("day02").map { it.split(" ").map { it.toInt() } }
        println(lines.count { it.isSafe() })
    }

    fun partTwo() {
        val lines = getLines("day02").map { it.split(" ").map { it.toInt() } }
        println(lines.count { it.isSafeWithAcceptance() })
    }
}

private fun List<Int>.isSafeWithAcceptance(): Boolean {
    val originalList = this
    val lists = mutableListOf<List<Int>>()
    originalList.indices.forEach {
        val newList = originalList.toMutableList()
        newList.removeAt(it)
        lists.add(newList)
    }
    return (lists.any { it.isSafe() } || originalList.isSafe())
}

private fun List<Int>.isSafe(): Boolean {
    val validDiffs = windowed(2).all {
        val diff = it[0] - it[1]
        diff.absoluteValue in 1..3
    }
    return validDiffs && (this == this.sorted() || this == this.sortedDescending())
}

