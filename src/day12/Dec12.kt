package day12

import getLines

fun main() {
    val solver = Dec12()
    solver.partOne()
}

class Dec12 {
    fun partOne() {
        val map = getLines("day12").map { it.toCharArray() }
        val regions = getRegions(map)
        println(regions.sumOf { it.price(map) })
    }

    fun getRegions(map: List<CharArray>): List<Region> {
        val visitedPoints = mutableSetOf<Point>()
        val regions = mutableListOf<Region>()
        map.forEachIndexed { i, chars ->
            chars.forEachIndexed { j, c ->
                if (!visitedPoints.contains(Point(i, j, c))) {
                    val region = getRegionForPlot(map, Point(i, j, c))
                    visitedPoints.addAll(region.plots)
                    regions.add(region)
                }
            }
        }
        return regions
    }

    fun getRegionForPlot(map: List<CharArray>, start: Point): Region {
        val visited = mutableSetOf(start)
        val queue = mutableListOf(start)
        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()

            val regionNeighbors = current.neighbors(map)
                .filter { it !in visited }
                .filter { it.value == start.value }

            for (neighbor in regionNeighbors) {
                visited += neighbor
                queue.add(neighbor)
            }
        }
        return Region(start.value, visited.toList())
    }


    data class Region(val type: Char, val plots: List<Point>) {
        fun price(map: List<CharArray>): Int {
            val area = plots.size
            var perimeter = 0
            plots.forEach {
                val neighbors = it.neighbors(map)
                perimeter += 4 - neighbors.size // map edges
                neighbors.forEach { neighbor ->
                    if (!plots.contains(neighbor)) {
                        perimeter++
                    }
                }
            }
            println("Region $type: area $area perimeter $perimeter")
            return area * perimeter
        }
    }

    data class Point(val x: Int, val y: Int, val value: Char) {
        fun neighbors(map: List<CharArray>): List<Point> {
            return listOfNotNull(
                if (map[this.x].size > this.y + 1) Point(
                    this.x,
                    this.y + 1,
                    map[this.x][this.y + 1]
                ) else null,
                if (this.y - 1 >= 0) Point(this.x, this.y - 1, map[this.x][this.y - 1]) else null,
                if (map.size > this.x + 1) Point(
                    this.x + 1,
                    this.y,
                    map[this.x + 1][this.y]
                ) else null,
                if (this.x - 1 >= 0) Point(this.x - 1, this.y, map[this.x - 1][this.y]) else null
            )
        }

    }
}

