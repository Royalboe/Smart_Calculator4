package calculator

fun main() {
    while (true) {
        val line = readLine()!!
        when {
            line == "" -> continue
            line == "/exit" -> {
                println("Bye!")
                break
            }
            line.contains(" ") -> {
                compute(line)
            }
            line == "/help" -> {
                println("This program is a calculator with addition and subtraction functionalities")
            }
            else -> {
                println(line)
            }
        }
    }
}

private fun compute(line: String) {
    val addWords = mutableListOf<String>()
    var addNumbers = 0
    var count = 0
    for (i in line.split(" ")) {
        when {
           i == " " -> continue
            i.contains("+") || i.contains("--") && i.length % 2 == 0 -> continue
            i.contains("--") && i.length % 2 != 0 || i == "-"-> {
                addWords.add("-")
            }
            else -> {
                addWords.add(i)
            }
        }
    }
    for (i in addWords) {
        when (i){
            "-" -> {
                addNumbers -= 2 * (addWords[count + 1].toInt())
            }
            else -> addNumbers += i.toInt()
        }
        count++
    }
    println(addNumbers)
}
