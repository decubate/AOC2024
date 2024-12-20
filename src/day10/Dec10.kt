package day10

import getLines

fun main() {
    val solver = Dec10()
    solver.partOne()
}

class Dec10 {
    fun partOne() {
        val map = getLines("day10").map { it.toCharArray() }
        val trailHeads = mutableListOf<Point>()
        map.forEachIndexed { i, chars ->
            chars.forEachIndexed { j, c ->
                if (c == '0') trailHeads.add(Point(i, j, 0))
            }
        }
        var score = 0
        trailHeads.forEach {
            score += it.score(map)
        }
        println(score)
    }
}

data class Point(val x: Int, val y: Int, val value: Int) {
    fun score(map: List<CharArray>): Int {
        val visited = mutableSetOf<Point>()
        val queue = mutableListOf(this)
        while (queue.isNotEmpty()) {
            val point = queue.removeFirst()
            if (visited.contains(point)) continue
            visited.add(point)

            val elevation = point.value
            val neighbors = listOfNotNull(
                if (map[point.x].size > point.y + 1) Point(point.x, point.y + 1, map[point.x][point.y + 1].toString().toInt()) else null,
                if (point.y - 1 >= 0) Point(point.x, point.y - 1, map[point.x][point.y - 1].toString().toInt()) else null,
                if (map.size > point.x + 1) Point(point.x + 1, point.y, map[point.x + 1][point.y].toString().toInt()) else null,
                if (point.x - 1 >= 0) Point(point.x - 1, point.y, map[point.x - 1][point.y].toString().toInt()) else null
            )
            neighbors.filter { it.value == elevation + 1 }.forEach {
                queue.add(it)
            }

        }

        return visited.count { it.value == 9 }
    }
}