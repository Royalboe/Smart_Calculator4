fun main() {
    val number = readLine()!!.toInt()
    val list = MutableList(number) { readLine()!!.toInt() }
    var old = 0
    var flag = "YES"
    for (i in list) {
        if (i >= old) {
            old = i
            continue
        } else {
            flag = "NO"
            break
        }
    }
    println(flag)
}
