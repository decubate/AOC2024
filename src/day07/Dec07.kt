package day07

import getLines

fun main() {
    val solver = Dec07()
    solver.partTwo()
}

class Dec07 {
    fun partOne() {
        val equations = getLines("day07").map {
            Equation(it.split(": ")[0].toLong(), it.split(": ")[1].split(" ").map { it.toLong() })
        }

        println(equations.filter { it.components.reversed().compute().contains(it.target) }.sumOf { it.target })
    }

    fun partTwo() {
        val equations = getLines("day07").map {
            Equation(it.split(": ")[0].toLong(), it.split(": ")[1].split(" ").map { it.toLong() })
        }

        println(equations.filter { it.components.reversed().computeAgain().contains(it.target) }.sumOf { it.target })
    }
}

private fun List<Long>.compute(): List<Long> {
    if (this.size == 1) return listOf(this.first())
    val firstValue = this.first()
    val rest = this.drop(1).compute()
    val added = rest.map { it + firstValue }
    val multiplied = rest.map { it * firstValue }
    return added.plus(multiplied)
}

private fun List<Long>.computeAgain(): List<Long> {
    if (this.size == 1) return listOf(this.first())
    val firstValue = this.first()
    val rest = this.drop(1).computeAgain()
    val added = rest.map { it + firstValue }
    val multiplied = rest.map { it * firstValue }
    val concatenated = rest.map { (it.toString() + firstValue.toString()).toLong() }
    return added.plus(multiplied).plus(concatenated)
}

data class Equation(val target: Long, val components: List<Long>)