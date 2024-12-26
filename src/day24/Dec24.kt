package day24

import getLines
import java.math.BigInteger

fun main() {
    val solver = Dec24()
    solver.partOne()
}


class Dec24 {
    fun partOne() {
        val lines = getLines("day24")
        val wires: MutableMap<String, Boolean?> =
            lines.takeWhile { it.isNotBlank() }.associate { it.split(": ").let { it[0] to it[1].let { it == "1" } } }
                .toMutableMap()
        val gates = lines.dropWhile { it.isNotBlank() }.drop(1).map {
            val split = it.split(" -> ")
            val outputWire = split[1]
            val inputWire1 = split[0].split(" ")[0]
            val inputWire2 = split[0].split(" ")[2]
            val operation = split[0].split(" ")[1]
            Gate(
                wires = (inputWire1 to inputWire2),
                outputWire = outputWire,
                operation = Operation.fromString(operation)
            )
        }
        gates.forEach {
            if (!wires.contains(it.wires.first)) {
                wires[it.wires.first] = null
            }
            if (!wires.contains(it.wires.second)) {
                wires[it.wires.second] = null
            }
            if (!wires.contains(it.outputWire)) {
                wires[it.outputWire] = null
            }
        }
        println(wires)
        println(gates)

        while (wires.any { it.key.startsWith("z") && it.value == null }) {
            gates.forEach {
                if (wires[it.wires.first] != null && wires[it.wires.second] != null) {
                    wires[it.outputWire] = it.operate(wires[it.wires.first]!!, wires[it.wires.second]!!)
                }
            }
        }

        println(
            BigInteger(
                wires.asSequence()
                .filter { it.key.startsWith("z") }
                .toList()
                .sortedByDescending { it.key }
                .map { if (it.value == true) 1 else 0 }
                .joinToString(""), 2)
        )
    }

    data class Gate(val wires: Pair<String, String>, val outputWire: String, val operation: Operation) {
        fun operate(firstValue: Boolean, secondValue: Boolean): Boolean {
            return when (operation) {
                Operation.AND -> (firstValue && secondValue)
                Operation.OR -> (firstValue || secondValue)
                Operation.XOR -> (firstValue != secondValue)
            }
        }
    }

    enum class Operation {
        AND,
        OR,
        XOR;

        companion object {
            fun fromString(value: String): Operation {
                return when (value) {
                    "XOR" -> XOR
                    "AND" -> AND
                    "OR" -> OR
                    else -> throw IllegalArgumentException("blah")
                }
            }
        }
    }
}