
import java.io.File
import java.io.InputStream

fun getLines(day: String): MutableList<String> {
    val file = File("src/$day/input.txt")
    println(file.absolutePath)
    val inputStream: InputStream = file.inputStream()
    val lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) }
    return lineList
}

fun getInputAsString(day: String): String {
    return getLines(day).fold("") { a, b -> a + b }
}