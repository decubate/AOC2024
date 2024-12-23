package day20

import getLines

fun main() {
    val solver = Dec20()
    solver.partOne()
}

class Dec20 {
    fun partOne() {
        val map = getLines("day20").map { it.toCharArray() }
        var start: Point? = null
        var end: Point? = null
        map.forEachIndexed { row, chars ->
            chars.forEachIndexed { col, c ->
                if (c == 'S') start = Point(col, row)
                if (c == 'E') end = Point(col, row)
            }
        }

        val path = bfs(start!!, end!!, map)
        val cheats = mutableListOf<Int>()
        println(path)
        path.forEach { (point, cost) ->

            val west = point.copy(y = point.y - 2)
            val east = point.copy(y = point.y + 2)
            val north = point.copy(x = point.x - 2)
            val south = point.copy(x = point.x + 2)

            listOf(west, east, north, south).forEach {
                if (path[it] != null) {
                    val potentialSavings = path[it]!! - (cost + 2)
                    if (potentialSavings > 0) {
                        cheats.add(potentialSavings)
                    }
                }
            }
        }

        println(cheats.filter { it >= 100 }.size)
    }

    fun bfs(start: Point, end: Point, map: List<CharArray>): MutableMap<Point, Int> {
        val visited = mutableMapOf<Point, Int>()
        val queueWithCost = mutableListOf(start to 0)
        while (queueWithCost.isNotEmpty()) {
            val (next, pathLength) = queueWithCost.removeFirst()

            visited[next] = pathLength

            if (next == end) break

            val neighbors = listOf(
                next.copy(x = next.x - 1),
                next.copy(x = next.x + 1),
                next.copy(y = next.y - 1),
                next.copy(y = next.y + 1),
            ).filter { it.x >= 0 && it.y >= 0 && it.x <= map.lastIndex && it.y <= map[it.x].lastIndex}
                .filter { map[it.y][it.x] != '#' }
                .filter { visited[it] == null }

            queueWithCost.addAll(neighbors.map { it to pathLength + 1 })
        }
        return visited
    }

    data class Point(val x: Int, val y: Int)
}