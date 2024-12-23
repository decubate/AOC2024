package day19

import getLines


fun main() {
    val solver = Dec19()
    solver.partOne()
}

class Dec19 {
    val possiblePatterns = mutableListOf<String>()
    val impossiblePatterns = mutableListOf<String>()

    fun partOne() {
        val lines = getLines("day19")
        val towels = lines[0].split(", ")
        val patterns = lines.drop(2)

        println(patterns.filter { it.isPossible(towels) }.size)
    }

    private fun String.isPossible(towels: List<String>): Boolean {
        println(this)
        if (possiblePatterns.contains(this)) {
            return true
        }
        if (impossiblePatterns.contains(this)) {
            return false
        }
        if (towels.contains(this)) {
            possiblePatterns.add(this)
            return true
        }
        if (this.isEmpty()) return true
        if (this.length == 1) {
            impossiblePatterns.add(this)
            return false
        }

        val towelPrefixes = towels.filter { this.startsWith(it) }

        if (towelPrefixes.isNotEmpty()) {
            return towelPrefixes.any {
                this.removePrefix(it).isPossible(towels).also {
                    if (it) {
                        possiblePatterns.add(this)
                    } else {
                        impossiblePatterns.add(this)
                    }
                }
            }

        }
        return false
    }

}


