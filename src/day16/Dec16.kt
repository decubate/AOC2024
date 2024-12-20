package day16

import getLines

fun main() {
    val solver = Dec16()
    solver.partOne()
}

class Dec16 {
    fun partOne() {
        val map = getLines("day16").map { it.toCharArray() }
        var start: Point? = null
        map.forEachIndexed { row, chars ->
            chars.forEachIndexed { col, c ->
                if (c == 'S') start = Point(col, row, Direction.EAST)
            }
        }

        println(getLowestCost(start!!, map))
    }

    private fun getLowestCost(start: Point, map: List<CharArray>): Int {
        val visited = mutableSetOf<PointWithCost>()
        val queue = mutableListOf(PointWithCost(point = start, cost = 0))
        var steps = 0
        val lowestCosts = mutableMapOf<Point, Int>()
        var lowestCost = Int.MAX_VALUE
        while (queue.isNotEmpty()) {
            if (steps % 1000000 == 0) println("steps: $steps, queue size: ${queue.size}")
            steps++
            val pointWithCost = queue.removeFirst()
            val lowestCostForPoint = lowestCosts[pointWithCost.point] ?: Int.MAX_VALUE
            if (pointWithCost.cost > lowestCostForPoint) continue
            if (pointWithCost.cost < lowestCostForPoint) {
                lowestCosts[pointWithCost.point] = pointWithCost.cost
            }
            if (map[pointWithCost.point.y][pointWithCost.point.x] == 'E') {
                if (pointWithCost.cost < lowestCost) {
                    lowestCost = pointWithCost.cost
                    println("lowest cost so far: $lowestCost")
                }
            }
            if (visited.contains(pointWithCost)) continue
            visited.add(pointWithCost)

            val direction = pointWithCost.point.direction

            val neighbors = Direction.entries.map {
                if (it == direction) it to 1
                else it to 1001
            }
            for ((nextDirection, score) in neighbors) {
                val nextPoint = pointWithCost.point.step(nextDirection)
                if (map[nextPoint.y][nextPoint.x] != '#') {
                    queue.add(PointWithCost(nextPoint.copy(direction = nextDirection), pointWithCost.cost + score))
                }
            }
        }
        return lowestCost
    }

    data class PointWithCost(val point: Point, val cost: Int)

    data class Point(val x: Int, val y: Int, val direction: Direction) {
        fun step(nextDirection: Direction): Point {
            return when (nextDirection) {
                Direction.NORTH -> Point(x, y - 1, direction)
                Direction.WEST -> Point(x - 1, y, direction)
                Direction.EAST -> Point(x + 1, y, direction)
                Direction.SOUTH -> Point(x, y + 1, direction)
            }
        }
    }

    enum class Direction {
        NORTH,
        WEST,
        EAST,
        SOUTH
    }
}