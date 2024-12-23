package day23

import getLines

fun main() {
    val solver = Dec23()
    solver.partOne()
}

class Dec23 {
    fun partOne() {
        val pairs = getLines("day23").map { it.split("-").sorted() }
        val connectionsPerComputer = mutableMapOf<String, MutableList<String>>()
        pairs.forEach { (first, second) ->
            if (connectionsPerComputer[first] == null) connectionsPerComputer[first] = mutableListOf()
            if (connectionsPerComputer[second] == null) connectionsPerComputer[second] = mutableListOf()
            connectionsPerComputer[first]?.add(second)
            connectionsPerComputer[second]?.add(first)
        }

        val setsOfThree = mutableSetOf<List<String>>()

        connectionsPerComputer.keys.forEach { initial ->
            val connectedComputers = connectionsPerComputer[initial] // second level
            connectedComputers?.forEach { second ->
                val connected = connectionsPerComputer[second] // third level
                connected?.forEach { third ->
                    if (connectionsPerComputer[third]?.contains(initial) == true) {
                        setsOfThree.add(listOf(initial, second, third).sorted())
                    }
                }
            }
        }

        println(setsOfThree.filter { it.any { it.startsWith("t") } }.size)
    }
}