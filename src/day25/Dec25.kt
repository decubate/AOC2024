package day25

import getLines

fun main() {
    val solver = Dec25()
    solver.partOne()
}

class Dec25 {
    fun partOne() {
        val locks = getLines("day25").chunked(8).filter { it[0] == "#####" }.map { it.filter { it.isNotEmpty() } }
        val keys = getLines("day25").chunked(8).filter { it[6] == "#####" }.map { it.filter { it.isNotEmpty() } }

        val locksAsHeights = locks.map { lockToHeight(it.map { it.toCharArray() }) }
        val keysAsHeights = keys.map { keyToHeight(it.map { it.toCharArray() }) }

        var numPairs = 0
        locksAsHeights.forEach { lock ->
            keysAsHeights.forEach { key ->
                if (matches(lock, key)) {
                    numPairs++
                }
            }
        }
        println(numPairs)
    }

    private fun matches(lock: MutableMap<Int, Int>, key: MutableMap<Int, Int>): Boolean {
        for (i in 0..<lock.size) {
            if (lock[i]!! + key[i]!! > 5) return false
        }
        return true
    }

    private fun lockToHeight(it: List<CharArray>): MutableMap<Int, Int> {
        val heights = mutableMapOf<Int, Int>()
        for (column in 0..4) {
            for (height in 0..6) {
                if (it[height][column] == '.') break
                heights[column] = height
            }
        }
        return heights
    }

    private fun keyToHeight(it: List<CharArray>): MutableMap<Int, Int> {
        val heights = mutableMapOf<Int, Int>()
        for (column in 0..4) {
            for (height in 6 downTo 0) {
                if (it[height][column] == '.') break
                heights[column] = (6 - height)
            }
        }
        return heights
    }
}