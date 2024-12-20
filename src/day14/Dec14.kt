package day14

import getLines

fun main() {
    val solver = Dec14()
    solver.partOne()
}

class Dec14 {
    fun partOne() {
        val regex = Regex("p=([0-9]+),([0-9]+) v=(-*[0-9]+),(-*[0-9]+)")
        val robots = getLines("day14").map {
            val values = regex.matchEntire(it)?.groupValues?.drop(1)!!
            Robot(Point(values[0].toInt(), values[1].toInt()), values[2].toInt(), values[3].toInt())
        }
        repeat(100) {
            robots.forEach { it.move() }
        }

        var (a, b, c, d) = listOf(0, 0, 0, 0)
        for (robot in robots) {
            if (robot.position.x <= 49 && robot.position.y <= 50) {
                a += 1
            } else if (robot.position.x >= 51 && robot.position.y <= 50) {
                b += 1
            } else if (robot.position.x <= 49 && robot.position.y >= 52) {
                c += 1
            } else if (robot.position.x >= 51 && robot.position.y >= 52) {
                d += 1
            }
        }
        println(a * b * c * d)
    }

    data class Point(val x: Int, val y: Int)

    data class Robot(var position: Point, val dx: Int, val dy: Int) {
        fun move() {
           position = position.copy(x = (position.x + dx + 101) % 101, y = (position.y + dy + 103) % 103)
        }
    }
}