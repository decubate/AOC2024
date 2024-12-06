package day04

import getLines

fun main() {
    val solver = Dec04()
    solver.partTwo()
}

class Dec04 {
    fun partOne() {
        val matrix = getLines("day04").map { it.toCharArray() }

        var numXmas = 0
        for ((i, array) in matrix.withIndex()) {
            for ((j, char) in array.withIndex()) {
                try {
                    if (forwardsXmas(char, matrix, i, j)) {
                        numXmas++
                    }
                    if (backwardsXmas(char, matrix, i, j)) {
                        numXmas++
                    }
                    if (downXmas(char, matrix, i, j)) {
                        numXmas++
                    }
                    if (upXmas(char, matrix, i, j)) {
                        numXmas++
                    }
                    if (downRightDiagonalXmas(char, matrix, i, j)) {
                        numXmas++
                    }
                    if (upRightDiagonalXmas(char, matrix, i, j)) {
                        numXmas++
                    }
                    if (downLeftDiagonalXmas(char, matrix, i, j)) {
                        numXmas++
                    }
                    if (upLeftDiagonalXmas(char, matrix, i, j)) {
                        numXmas++
                    }
                } catch (_: Exception) {}
            }
        }

        println(numXmas)
    }

    fun partTwo() {
        val matrix = getLines("day04").map { it.toCharArray() }

        var numXmas = 0
        for ((i, array) in matrix.withIndex()) {
            for ((j, char) in array.withIndex()) {
                try {
                    if (char == 'A' && isMas(matrix, i, j)) {
                        numXmas++
                    }
                } catch (_: Exception) {}
            }
        }

        println(numXmas)
    }

    private fun isMas(matrix: List<CharArray>, i: Int, j: Int): Boolean {
        if (matrix[i-1][j-1] == 'M' && matrix[i+1][j+1] == 'S' && matrix[i-1][j+1] == 'M' && matrix[i+1][j-1] == 'S') {
            return true
        }
        if (matrix[i-1][j-1] == 'M' && matrix[i+1][j+1] == 'S' && matrix[i-1][j+1] == 'S' && matrix[i+1][j-1] == 'M') {
            return true
        }

        if (matrix[i-1][j-1] == 'S' && matrix[i+1][j+1] == 'M' && matrix[i-1][j+1] == 'M' && matrix[i+1][j-1] == 'S') {
            return true
        }
        if (matrix[i-1][j-1] == 'S' && matrix[i+1][j+1] == 'M' && matrix[i-1][j+1] == 'S' && matrix[i+1][j-1] == 'M') {
            return true
        }

        return false
    }

    private fun downRightDiagonalXmas(char: Char, matrix: List<CharArray>, i: Int, j: Int): Boolean {
        try {
            return char == 'X' && matrix[i + 1][j + 1] == 'M' && matrix[i + 2][j + 2] == 'A' && matrix[i + 3][j + 3] == 'S'
        } catch (_: Exception) {
            return false
        }
    }

    private fun upRightDiagonalXmas(char: Char, matrix: List<CharArray>, i: Int, j: Int): Boolean {
        try {
            return char == 'X' && matrix[i - 1][j + 1] == 'M' && matrix[i - 2][j + 2] == 'A' && matrix[i - 3][j + 3] == 'S'
        } catch (_: Exception) {
            return false
        }
    }

    private fun downLeftDiagonalXmas(char: Char, matrix: List<CharArray>, i: Int, j: Int): Boolean {
        try {
            return char == 'X' && matrix[i + 1][j - 1] == 'M' && matrix[i + 2][j - 2] == 'A' && matrix[i + 3][j - 3] == 'S'
        } catch (_: Exception) {
            return false
        }
    }

    private fun upLeftDiagonalXmas(char: Char, matrix: List<CharArray>, i: Int, j: Int): Boolean {
        try {
            return char == 'X' && matrix[i - 1][j - 1] == 'M' && matrix[i - 2][j - 2] == 'A' && matrix[i - 3][j - 3] == 'S'
        } catch (_: Exception) {
            return false
        }
    }

    private fun downXmas(
        char: Char,
        matrix: List<CharArray>,
        i: Int,
        j: Int
    ): Boolean {
        try {
            return char == 'X' && matrix[i + 1][j] == 'M' && matrix[i + 2][j] == 'A' && matrix[i + 3][j] == 'S'
        } catch (_: Exception) {
            return false
        }
    }

    private fun upXmas(
        char: Char,
        matrix: List<CharArray>,
        i: Int,
        j: Int
    ): Boolean {
        try {
            return char == 'X' && matrix[i - 1][j] == 'M' && matrix[i - 2][j] == 'A' && matrix[i - 3][j] == 'S'
        } catch (_: Exception) {
            return false
        }
    }

    private fun forwardsXmas(
        char: Char,
        matrix: List<CharArray>,
        i: Int,
        j: Int
    ): Boolean {
        try {
            return char == 'X' && matrix[i][j + 1] == 'M' && matrix[i][j + 2] == 'A' && matrix[i][j + 3] == 'S'
        } catch (_: Exception) {
            return false
        }
    }

    private fun backwardsXmas(
        char: Char,
        matrix: List<CharArray>,
        i: Int,
        j: Int
    ): Boolean {
        try {
            return char == 'X' && matrix[i][j - 1] == 'M' && matrix[i][j - 2] == 'A' && matrix[i][j - 3] == 'S'
        } catch (_: Exception) {
            return false
        }
    }
}