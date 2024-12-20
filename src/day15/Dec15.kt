package day15

import getLines

fun main() {
    val solver = Dec15()
    solver.partOne()
}

class Dec15 {
    fun partOne() {
        val map = getLines("day15").takeWhile { it.firstOrNull() == '#' }.map { it.toCharArray() }
        val instructions = getLines("day15").takeLastWhile {
            it.firstOrNull() != '#' && it != "\n"
        }.fold("") { a, b -> a + b }

        var robot: Robot? = null
        var boxes = mutableListOf<Point>()
        val walls = mutableListOf<Point>()

        map.forEachIndexed { x, chars ->
            chars.forEachIndexed { y, c ->
                when (c) {
                    '@' -> robot = Robot(position = Point(x, y))
                    'O' -> boxes.add(Point(x, y))
                    '#' -> walls.add(Point(x, y))

                }
            }
        }

        instructions.forEach {
            boxes = robot!!.step(Instruction.fromChar(it), boxes, walls, map[0].size, map.size)
        }

        println(boxes)
        println(robot)
        println(boxes.sumOf {
            it.y + it.x * 100
        })
    }

    data class Robot(var position: Point) {
        fun step(
            instruction: Instruction,
            boxes: MutableList<Point>,
            walls: List<Point>,
            width: Int,
            height: Int
        ): MutableList<Point> {
            val nextPosition = position.move(instruction)
            if (walls.contains(nextPosition)) {
                return boxes
            }
            if (boxes.contains(nextPosition)) {
                val boxesToPush = mutableListOf<Point>()
                var boxPosition = nextPosition
                while (boxes.contains(boxPosition)) {
                    boxesToPush.add(boxPosition)
                    boxPosition = boxPosition.move(instruction)
                }
                if (walls.contains(boxPosition)) {
                    return boxes
                }
                boxes.removeAll(boxesToPush)
                boxes.addAll(boxesToPush.map { it.move(instruction) })
                position = nextPosition
            } else {
                position = nextPosition
            }

            return boxes
        }

        private fun printState(
            position: Point,
            boxes: MutableList<Point>,
            walls: List<Point>,
            width: Int,
            height: Int
        ) {
            for (x in 0..< width) {
                for (y in 0..< height) {
                    if (position.x == x && position.y == y) print('@')
                    else if (walls.contains(Point(x, y))) print('#')
                    else if (boxes.contains(Point(x, y))) print('O')
                    else print('.')
                }
                println()
            }
        }
    }

    data class Point(val x: Int, val y: Int) {
        fun move(instruction: Instruction): Point {
            return when (instruction) {
                Instruction.UP -> this.copy(this.x - 1, this.y)
                Instruction.DOWN -> this.copy(this.x + 1, this.y)
                Instruction.LEFT -> this.copy(this.x, this.y - 1)
                Instruction.RIGHT -> this.copy(this.x, this.y + 1)
            }
        }
    }

    enum class Instruction {
        UP,
        DOWN,
        LEFT,
        RIGHT;

        companion object {
            fun fromChar(c: Char): Instruction {
                return when (c) {
                    '^' -> UP
                    'v' -> DOWN
                    '>' -> RIGHT
                    '<' -> LEFT
                    else -> throw IllegalStateException("")
                }
            }
        }
    }
}