package day09

import getLines

fun main() {
    val solver = Dec09()
    solver.partOne()
}

class Dec09 {
    fun partOne() {
        val diskMap = getLines("day09")[0]
        val completeMemory = getCompleteMemory(diskMap).toMutableList()

        while (true) {
            val rightMostBlock = completeMemory.indexOfLast { it != null }
            val leftMostSpace = completeMemory.indexOfFirst { it == null }

            if (rightMostBlock < leftMostSpace) break

            completeMemory[leftMostSpace] = completeMemory[rightMostBlock]
            completeMemory[rightMostBlock] = null

        }

        println(completeMemory.checkSum())

    }

    private fun getCompleteMemory(diskMap: String): List<Int?> {
        var currentIndex = 0

        val ret = mutableListOf<Int?>()
        diskMap.forEachIndexed { index, c ->
            if (index % 2 == 0) { // file
                val numBlocks = c.toString().toInt()
                for (i in 0..<numBlocks) {
                    ret.add(currentIndex)
                }
                currentIndex++
            } else {
                val numBlocks = c.toString().toInt()
                for (i in 0..<numBlocks) {
                    ret.add(null)
                }
            }
        }
        return ret
    }
}

private fun List<Int?>.checkSum(): Long {
    return mapIndexed { index, i ->
        if (i == null) 0L else {
            index * i.toLong()
        }
    }.sum()
}
