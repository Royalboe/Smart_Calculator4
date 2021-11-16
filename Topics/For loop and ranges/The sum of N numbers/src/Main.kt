fun main() {
    val n = readLine()!!.toInt()
    var sum = 0
    for (i in 1..n) {
        val d = readLine()!!.toInt()
        sum += d
    }
    println(sum)
}
