fun main() {
    val a = readLine()!!.toInt()
    val b = readLine()!!.toInt()
    val c = readLine()!!.toInt()
    val d = readLine()!!.toInt()
    for (x in 0..1000) {
        val equation = a * x * x * x + b * x * x + c * x + d
        if (equation == 0) {
            println(x)
        } 
    }
}
