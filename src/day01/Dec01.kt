package day01

import getLines
import kotlin.math.absoluteValue

fun main() {
    val solver = Dec01()
    solver.partTwo()
}

class Dec01 {
    fun partOne() {
        val lines = getLines("day01")
        val firstList = lines.map { it.split("   ")[0].toInt() }
        val secondList = lines.map { it.split("   ")[1].toInt()}
        println(firstList.sorted().zip(secondList.sorted()) { a, b -> (a - b).absoluteValue }.sum())
    }

    fun partTwo() {
        val lines = getLines("day01")
        val firstList = lines.map { it.split("   ")[0].toInt() }
        val secondList = lines.map { it.split("   ")[1].toInt()}
        val similarityScores = mutableMapOf<Int, Int>()
        firstList.forEachIndexed { index, firstNumber ->
            similarityScores[index] = secondList.count { it == firstNumber} * firstNumber
        }
        println(similarityScores.values.sum())
    }
}