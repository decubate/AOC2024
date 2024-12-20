package day13

import getLines

fun main() {
    val solver = Dec13()
    solver.partOne()
}

class Dec13 {
    fun partOne() {
        val lines = getLines("day13")
        val machines = mutableListOf<ClawMachine>()

        lines.chunked(4).forEach { machineInput ->
            val buttonA = machineInput[0].removePrefix("Button A: ")
            val buttonB = machineInput[1].removePrefix("Button B: ")
            val prize = machineInput[2].removePrefix("Prize: ")
            machines.add(
                ClawMachine(
                    a = Button(
                        dx = buttonA.split(", ")[0].split("+")[1].toInt(),
                        dy = buttonA.split(", ")[1].split("+")[1].toInt()
                    ),
                    b = Button(
                        dx = buttonB.split(", ")[0].split("+")[1].toInt(),
                        dy = buttonB.split(", ")[1].split("+")[1].toInt()
                    ),
                    prize = Point(
                        x = prize.split(", ")[0].split("=")[1].toInt(),
                        y = prize.split(", ")[1].split("=")[1].toInt(),
                    )
                )
            )
        }
        println(machines.sumOf { it.cheapest() })
    }

    data class ClawMachine(val a: Button, val b: Button, val prize: Point) {
        fun cheapest(): Int {
            val solutions = mutableSetOf<Pair<Int, Int>>()
            for (numA in 0 .. 100) {
                for (numB in 0 .. 100) {
                    if (Point(numA*a.dx + numB*b.dx, numA*a.dy + numB*b.dy) == prize) {
                        solutions.add(numA to numB)
                    }
                }
            }
            println("solutions: $solutions")
            val costs = solutions.map { (a, b) -> a*3+b }.sorted()
            return costs.firstOrNull() ?: 0
        }
    }

    data class Button(val dx: Int, val dy: Int)

    data class Point(val x: Int, val y: Int)
}