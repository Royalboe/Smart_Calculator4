fun main() {
    val number = readLine()!!.toInt()
    val list = MutableList(number) { readLine()!!.toInt() }
    var count = 0
    var newCount = 0
    var old = 0
    for (i in list) {
        if (i >= old) {
            count++
            old = i
        } else {
            old = i
            if (newCount >= count) {
                count = 1
            } else {
                newCount = count
                count = 1
            }
        }
    }
    if (count > newCount) {
        newCount = count
    }
    println(newCount)
}
