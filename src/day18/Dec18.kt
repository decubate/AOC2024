package day18

import getLines

fun main() {
    val solver = Dec18()
    solver.partOne()
}

class Dec18 {
    fun partOne() {
        val gridsize = 70
        val numBytes = 1024

        val bytes: List<Point> = getLines("day18").map { it.split(",").map { it.toInt() } }.take(numBytes).map { Point(it[0], it[1]) }

        println(bfs(gridsize, bytes))
    }

    fun bfs(gridsize: Int, bytes: List<Point>): Int {
        val start = Point(0, 0)
        val end = Point(gridsize, gridsize)
        val visited = mutableMapOf<Point, Int>()
        val queueWithCost = mutableListOf(start to 0)
        while (queueWithCost.isNotEmpty()) {
            val (next, pathLength) = queueWithCost.removeFirst()
            if (visited[next] != null) continue

            visited[next] = pathLength

            if (next == end) break

            val neighbors = listOf(
                next.copy(x = next.x - 1),
                next.copy(x = next.x + 1),
                next.copy(y = next.y - 1),
                next.copy(y = next.y + 1),
            ).filter { it.x >= 0 && it.y >= 0 && it.x <= gridsize && it.y <= gridsize }
                .filter { !bytes.contains(Point(it.x,it.y)) }
                .filter { visited[it] == null }

            queueWithCost.addAll(neighbors.map { it to pathLength + 1 })
        }
        return visited.filter { it.key == end }.map { it.value }.first()
    }
}

data class Point(val x: Int, val y: Int)