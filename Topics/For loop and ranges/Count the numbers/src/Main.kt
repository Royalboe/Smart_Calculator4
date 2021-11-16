fun main() {
    val a = readLine()!!.toInt()
    val b = readLine()!!.toInt()
    val c = readLine()!!.toInt()
    var sum = 0
    for (i in a..b) {
        if (i % c == 0)
            sum++
    }
    println(sum)
}
