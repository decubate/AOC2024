package day21

import getLines

fun main() {
    val solver = Dec21()
    solver.partOne()
}

class Dec21 {
    fun partOne() {
        val codes = getLines("day21")
        println(codes.sumOf { minSteps(sequence = it, limit = 2, depth = 0) })
    }

    private fun minSteps(sequence: String, limit: Int, depth: Int): Int {
        return 0
    }

    /**
     * The solution is to define a function called dp(sequence, limit, depth).
     * The 'sequence' is the current code that needs to be entered.
     * The 'limit' is how deep we need to go (2 for part 1, 25 for part 2). The 'depth' is how deep we currently are in the recursive calls.
     * The dp function is first called with the initial state, like dp("029A", 25, 0). It then computes the shortest path the next robot could take to press a key in the current state.
     * It generates all permutations of that path. For example, if the path is <<v, the permutations would be [<<v, <v<, v<<].
     * The function then recursively calls itself with each permutation, incrementing the depth. It uses the path that results in the least total steps for all the robots below it.
     * In theory, this would generate a huge state space. But in my input, the shortest path only presses a button in 2828 unique ways.
     * By caching the function results and returning early whenever we've seen the same sequence before, we can speed it up significantly. It runs in about 30ms for both parts.
     */
}