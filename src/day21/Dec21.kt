package day21

import getLines

fun main() {
    val solver = Dec21()
    solver.partOne()
}

class Dec21 {
    val cache = mutableMapOf<Pair<Int, String>, Long>()

    fun partOne() {
        val codes = getLines("day21")
        println(codes.sumOf {
            it.removeSuffix("A").toInt() * minSteps(
                sequence = it,
                limit = 2,
                depth = 0,
                keyboard = Numpad
            )
        })
    }

    fun partTwo() {
        val codes = getLines("day21")
        println(codes.sumOf {
            it.removeSuffix("A").toInt() * minSteps(
                sequence = it,
                limit = 25,
                depth = 0,
                keyboard = Numpad
            )
        })
    }

    private fun minSteps(sequence: String, limit: Int, depth: Int, keyboard: Keyboard): Long {
        if (cache.contains(depth to sequence)) return cache[depth to sequence]!!
        return "A$sequence".windowed(2).sumOf { fromTo ->
            val (from, to) = (fromTo[0] to fromTo[1])

            val allPermutations = keyboard.paths[from to to]!!

            if (depth == limit)
                return@sumOf allPermutations.minOf { it.length.toLong() }

            return@sumOf allPermutations.minOf { permutation ->
                minSteps(permutation, limit, depth + 1, Directional)
            }
        }.also {
            cache[depth to sequence] = it
        }
    }

    abstract class Keyboard(val graph: Map<Char, List<Edge>>) {

        val paths: Map<Pair<Char, Char>, List<String>> = buildMap {
            graph.keys.forEach { from ->
                graph.keys.forEach { to ->
                    put(Pair(from, to), allWalks(from, to))
                }
            }
        }

        private fun allWalks(from: Char, to: Char): List<String> {
            data class Step(
                val current: Char,
                val steps: List<Char>,
                val history: Set<Char>
            )

            val result = mutableListOf<String>()
            val queue = mutableListOf(Step(from, emptyList(), setOf(from)))
            while (queue.isNotEmpty()) {
                val (current, steps, history) = queue.removeFirst()

                if (current == to) {
                    result += steps.joinToString("") + "A"
                    continue
                }

                val neighbors = graph[current]!!
                    .filter { it.destination !in history }
                for ((dir, key) in neighbors) {
                    queue += Step(key, steps + dir, history + key)
                }
            }

            val shortest = result.minOf { it.length }
            return result.filter { it.length == shortest }
        }
    }

    object Numpad : Keyboard(
        graph = mapOf(
            'A' to listOf(
                Edge('<', '0'),
                Edge('^', '3')
            ),
            '0' to listOf(
                Edge('>', 'A'),
                Edge('^', '2')
            ),
            '1' to listOf(
                Edge('>', '2'),
                Edge('^', '4'),
            ),
            '2' to listOf(
                Edge('>', '3'),
                Edge('^', '5'),
                Edge('v', '0'),
                Edge('<', '1'),
            ),
            '3' to listOf(
                Edge('^', '6'),
                Edge('v', 'A'),
                Edge('<', '2'),
            ),
            '4' to listOf(
                Edge('>', '5'),
                Edge('^', '7'),
                Edge('v', '1'),
            ),
            '5' to listOf(
                Edge('>', '6'),
                Edge('^', '8'),
                Edge('v', '2'),
                Edge('<', '4'),
            ),
            '6' to listOf(
                Edge('^', '9'),
                Edge('v', '3'),
                Edge('<', '5'),
            ),
            '7' to listOf(
                Edge('>', '8'),
                Edge('v', '4'),
            ),
            '8' to listOf(
                Edge('>', '9'),
                Edge('v', '5'),
                Edge('<', '7'),
            ),
            '9' to listOf(
                Edge('v', '6'),
                Edge('<', '8'),
            ),
        )
    )

    object Directional : Keyboard(
        graph = mapOf(
            'A' to listOf(
                Edge('<', '^'),
                Edge('v', '>')
            ),
            '^' to listOf(
                Edge('>', 'A'),
                Edge('v', 'v')
            ),
            '>' to listOf(
                Edge('^', 'A'),
                Edge('<', 'v')
            ),
            'v' to listOf(
                Edge('^', '^'),
                Edge('<', '<'),
                Edge('>', '>'),
            ),
            '<' to listOf(
                Edge('>', 'v'),
            ),
        )
    )

    data class Edge(val direction: Char, val destination: Char)

    /**
     * Reddit wisdom:
     *
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