package day06

import getLines

fun main() {
    val solver = Dec06()
    solver.partTwo()
}

class Dec06 {
    fun partOne() {
        val matrix = getLines("day06").map { it.toCharArray() }
        val position: Point = getPosition(matrix, '^')!!
        val direction = Direction.UP

        println(getVisitedPoints(position, direction, matrix).size)
    }

    fun partTwo() {
        val startTime = System.currentTimeMillis()
        val matrix = getLines("day06").map { it.toCharArray() }
        val position: Point = getPosition(matrix, '^')!!
        val direction = Direction.UP

        var numLoops = 0
        var currentMatrix = 0

        getVisitedPoints(position, direction, matrix).also { println(it) }.drop(1).forEach {
            matrix[it.first][it.second] = '#'
            if (currentMatrix % 1000 == 0) {
                println("checking matrix $currentMatrix")
            }
            if (matrix.containsLoop(position, direction)) {
                numLoops++
            }
            matrix[it.first][it.second] = '.'
            currentMatrix++
        }

        println(numLoops)
        println("took ${System.currentTimeMillis() - startTime}ms")
    }

    private fun getVisitedPoints(
        pos: Point,
        dir: Direction,
        matrix: List<CharArray>
    ): MutableSet<Pair<Int, Int>> {
        var position = pos
        var direction = dir
        val visitedPoints = mutableSetOf(Pair(position.i, position.j))
        try {
            while (true) {
                var nextPosition = position.getNextPosition(direction, matrix)
                while (nextPosition.value == '#') {
                    direction = direction.getNext()
                    nextPosition = position.getNextPosition(direction, matrix)
                }
                position = nextPosition
                visitedPoints.add(Pair(position.i, position.j))
            }
        } catch (_: Exception) {
            return visitedPoints
        }
    }

    private fun getPosition(matrix: List<CharArray>, s: Char): Point? {
        matrix.forEachIndexed { i, chars ->
            chars.forEachIndexed { j, c ->
                if (c == s) {
                    return Point(i, j, s)
                }
            }
        }
        return null
    }
}

private fun List<CharArray>.containsLoop(pos: Point, dir: Direction): Boolean {
    var position = pos
    val visitedPoints = mutableSetOf(PointWithDirection(pos.i, pos.j, dir))
    var direction = dir
    try {
        while (true) {
            var nextPosition = position.getNextPosition(direction, this)
            while (nextPosition.value == '#') {
                direction = direction.getNext()
                nextPosition = position.getNextPosition(direction, this)
            }
            position = nextPosition
            if (visitedPoints.contains(PointWithDirection(position.i, position.j, direction))) {
                return true
            }
            visitedPoints.add(PointWithDirection(position.i, position.j, direction))
        }
    } catch (_: Exception) {
        return false
    }
}

data class PointWithDirection(val i: Int, val j: Int, val direction: Direction)

data class Point(val i: Int, val j: Int, val value: Char) {

    fun getNextPosition(direction: Direction, matrix: List<CharArray>): Point {
        return when (direction) {
            Direction.UP -> Point(i-1, j, matrix[i-1][j])
            Direction.DOWN -> Point(i + 1, j, matrix[i+1][j])
            Direction.LEFT -> Point(i, j - 1, matrix[i][j-1])
            Direction.RIGHT -> Point(i, j + 1, matrix[i][j+1])
        }
    }
}

enum class Direction {
    UP, DOWN, LEFT, RIGHT;

    fun getNext(): Direction {
        return when (this) {
            UP -> RIGHT
            DOWN -> LEFT
            LEFT -> UP
            RIGHT -> DOWN
        }
    }
}