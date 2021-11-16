package calculator

fun main() {
    while (true) {
        val line = readLine()!!
        when {
            line == "" -> continue
            line.contains("/") -> {
                if (commands(line)) break
            }
            line.isNotEmpty() -> {
                try {
                    compute(line)
                } catch (e: Exception) {
                    println("Invalid expression")
                }
            }
        }
    }
}

private fun commands(line: String): Boolean {
    when (line) {
        "/exit" -> {
            println("Bye!")
            return true
        }
        "/help" -> {
            println("This program is a calculator with addition and subtraction functionalities")
        }
        else -> {
            println("Unknown command")
        }
    }
    return false
}

private fun compute(line: String) {
    val addWords = mutableListOf<String>()
    var addNumbers = 0
    val invalidRegex = Regex("([\\d]+[+|-]+|[a-zA-Z]+)")
    when {
        line.matches(invalidRegex) -> {
            println("Invalid expression")
        }
        !line.contains(" ") -> {
            val regex = Regex("[+][\\d]+")
            if (line.matches(regex)) {
                println(line.replace("+", ""))
            }else println(line)
        }
        else -> {
            getDigits(line, addWords)
            addNumbers = performComputation(addWords, addNumbers)
            println(addNumbers)
        }
    }
}

private fun performComputation(
    addWords: MutableList<String>,
    addNumbers: Int
): Int {
    var addNumbers1 = addNumbers
    try {
        for ((count, i) in addWords.withIndex()) {
            when (i) {
                "-" -> {
                    addNumbers1 -= 2 * (addWords[count + 1].toInt())
                }
                else -> addNumbers1 += i.toInt()
            }
        }
    } catch (e: Exception) {
        println("Invalid expression")
    }
    return addNumbers1
}

private fun getDigits(line: String, addWords: MutableList<String>) {
    for (i in line.split(" ")) {

        when {
            i == " " -> continue
            i.contains("+") || i.contains("--") && i.length % 2 == 0 -> continue
            i.contains("--") && i.length % 2 != 0 || i == "-" -> {
                addWords.add("-")
            }
            else -> {
                addWords.add(i)
            }
        }
    }
}
