package day08

import getLines

fun main() {
    val solver = Dec08()
    solver.partOne()
}

class Dec08 {
    fun partOne() {
        val matrix = getLines("day08").map { it.toCharArray() }
        val frequencyPairs = getFrequencyPairs(matrix)

        val antinodes = mutableSetOf<Point>()
        frequencyPairs.forEach { (first, second) ->
            val dxFirst = first.x - second.x
            val dyFirst = first.y - second.y

            val dxSecond = second.x - first.x
            val dySecond = second.y - first.y

            antinodes.add(Point(first.x + dxFirst, first.y + dyFirst, '#'))
            antinodes.add(Point(second.x + dxSecond, second.y + dySecond, '#'))
        }

        var numAntinodes = 0
        matrix.forEachIndexed { i, chars ->
            chars.forEachIndexed { j, c ->
                if (antinodes.contains(Point(i,j, '#'))) numAntinodes++
            }
        }
        println(numAntinodes)
    }

    private fun getFrequencyPairs(matrix: List<CharArray>): List<Pair<Point, Point>> {
        val frequencyPoints = mutableMapOf<Char, List<Point>>()
        matrix.forEachIndexed { i, chars ->
            chars.forEachIndexed { j, c ->
                if (c != '.') {
                    val currentList = frequencyPoints[c] ?: mutableListOf()
                    frequencyPoints[c] = currentList.plus(Point(i, j, c))
                }
            }
        }
        val frequencyPairs = mutableListOf<Pair<Point, Point>>()
        frequencyPoints.values.forEach { list ->
            for (i in list.indices) {
                for (j in (i+1..list.lastIndex)) {
                    frequencyPairs += (list[i] to list[j])
                }
            }
        }
        return frequencyPairs
    }

    data class Point(val x: Int, val y: Int, val value: Char)
}
