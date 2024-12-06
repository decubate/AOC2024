package day05

import java.io.File
import java.io.InputStream

fun main() {
    val solver = Dec05()
    solver.partTwo()
}

class Dec05 {
    fun partOne() {
        val (instructions, updates) = getInput("day05")

        val validUpdates: List<List<Int>> = getValidUpdates(instructions, updates)

        println(validUpdates.sumOf { it[it.size / 2] })
    }

    fun partTwo() {
        val (instructions, updates) = getInput("day05")

        val invalidUpdates: List<List<Int>> = getInvalidUpdates(instructions, updates)

        println(invalidUpdates.map { it.sortByInstructions(instructions) }.sumOf { it[it.size / 2] })
    }

    private fun getValidUpdates(instructions: List<Instruction>, updates: List<List<Int>>): List<List<Int>> {
        return updates.filter { it.isValid(instructions) }
    }

    private fun getInvalidUpdates(instructions: List<Instruction>, updates: List<List<Int>>): List<List<Int>> {
        return updates.filter { !it.isValid(instructions) }
    }
}

private fun List<Int>.sortByInstructions(instructions: List<Instruction>): List<Int> {
    return this.sortedWith { first, second ->
        if (instructions.contains(Instruction(first, second))) {
            -1
        } else if (instructions.contains(Instruction(second, first))) {
            1
        } else {
            0
        }
    }
}

private fun List<Int>.isValid(instructions: List<Instruction>): Boolean {
    val relevantInstructions = getRelevantInstructions(this, instructions)
    return relevantInstructions.all {
        indexOf(it.first) < indexOf(it.second)
    }
}

fun getRelevantInstructions(ints: List<Int>, instructions: List<Instruction>): List<Instruction> {
    return instructions.filter { ints.contains(it.first) && ints.contains(it.second) }
}

fun getInput(day: String): Pair<List<Instruction>, List<List<Int>>> {
    val file = File("src/$day/input.txt")
    println(file.absolutePath)
    val inputStream: InputStream = file.inputStream()
    val lineList = mutableListOf<String>()

    var instructions = true
    val instructionList = mutableListOf<Instruction>()
    val updates = mutableListOf<List<Int>>()
    inputStream.bufferedReader().forEachLine {
        if (it.isBlank()) {
            instructions = false
        } else {
            if (instructions) {
                val ints = it.split("|").map { it.toInt() }
                instructionList.add(Instruction(ints[0], ints[1]))
            } else {
                val ints = it.split(",").map { it.toInt() }
                updates.add(ints)
            }
            lineList.add(it)
        }
    }
    return instructionList to updates
}

data class Instruction(val first: Int, val second: Int)