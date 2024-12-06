package day03

import getInputAsString
import getLines

fun main() {
    val solver = Dec03()
    solver.partTwo()
}

class Dec03 {
    fun partOne() {
        val input = getInputAsString("day03")

        println(getInstructions(input).map { instruction ->
            instruction.calculate()
        }.sum())
    }

    fun partTwo() {
        val input = getInputAsString("day03")

        println(getEnabledInstructions(input).sumOf { instruction ->
            instruction.calculate()
        })
    }

    private fun getInstructions(input: String): Sequence<String> {
        val regex = Regex("mul\\([0-9]{1,3},[0-9]{1,3}\\)")
        val results = regex.findAll(input)
        return results.map {
            it.value
        }
    }

    private fun getInstructionsWithExtraKeywords(input: String): Sequence<String> {
        val regex = Regex("(mul\\([0-9]{1,3},[0-9]{1,3}\\)|do\\(\\)|don't\\(\\))")
        val results = regex.findAll(input)
        return results.map {
            it.value
        }
    }

    private fun getEnabledInstructions(input: String): List<String> {
        val instructions: Sequence<String> = getInstructionsWithExtraKeywords(input)
        val enabledInstructions = mutableListOf<String>()
        var enabled = true
        instructions.forEach {
            if (it == "don't()") {
                enabled = false
            } else if (it == "do()") {
                enabled = true
            } else if (enabled) {
                enabledInstructions.add(it)
            }
        }
        return enabledInstructions
    }
}

private fun String.calculate(): Int {
    val regex = Regex("mul\\(([0-9]{1,3}),([0-9]{1,3})\\)")
    val numbers = regex.findAll(this).flatMap {
        it.groupValues.drop(1).map { it.toInt() }
    }.toList()
    return numbers[0]*numbers[1]
}
