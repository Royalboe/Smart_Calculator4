fun main() {
    val a = readLine()!!.toInt()
    val b = readLine()!!.toInt()
    var sum = 0
    for (i in a..b) {
        sum += i
    }
    println(sum)
}
