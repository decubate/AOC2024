package day09

import getLines

fun main() {
    val solver = Dec09()
    solver.partTwo()
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

    fun partTwo() {
        val diskMap = getLines("day09")[0]
        val completeMemory = getCompleteMemory(diskMap).toMutableList()

        val originalBlocks = mutableListOf<Int>()
        completeMemory.forEach {
            if (it != null && !originalBlocks.contains(it)) originalBlocks.add(it)
        }
        originalBlocks.sortDescending()

        for (block in originalBlocks) {
            val indexOfFirst = completeMemory.indexOfFirst { it == block }
            val indexOfLast = completeMemory.indexOfLast { it == block }
            val sizeOfBlock = (indexOfLast - indexOfFirst) + 1

            var lengthOfSpace = 0
            for (i in 0..<completeMemory.size) {
                if (i >= indexOfFirst) break
                val place = completeMemory[i]
                if (place == null) {
                    lengthOfSpace++
                } else {
                    lengthOfSpace = 0
                }
                if (lengthOfSpace == sizeOfBlock) {
                    for (j in (i - (lengthOfSpace - 1))..i) {
                        completeMemory[j] = block
                    }
                    for (k in indexOfFirst..indexOfLast) {
                        completeMemory[k] = null
                    }
                    break
                }
            }
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
