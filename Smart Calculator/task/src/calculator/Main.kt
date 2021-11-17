package calculator

fun main() {
    val digitsMap = mutableMapOf<String, Int>()
    while (true) {
        val input = readLine()!!
        when {
            input == "" -> continue
            input.contains("/") -> {
                if (commands(input)) {
                    digitsMap.clear()
                    break
                }
            }
            input.isNotEmpty() -> {
                try {
                    compute(input, digitsMap)
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

private fun compute(line: String, identifiers: MutableMap<String, Int>) {
    val regex = Regex("""\s*""")
    val newLine = line.replace(regex, "")
    val addWords = mutableListOf<String>()
    val invalidRegex = Regex("([\\d]+[+|-]+)")
    when {
        line.contains("=") -> {
            // To assign variables to store values
            assignVariables(newLine, identifiers)
        }
        line.matches(invalidRegex) -> {
            println("Invalid expression")
        }
        !line.contains(" ") -> {
            if (line.matches("[+][\\d]+".toRegex())) {
                println(line.replace("+", ""))
            } else if (line.matches("[a-zA-Z]+".toRegex())) {
                if (identifiers.containsKey(line)) {
                    println(identifiers[line])
                } else {
                    println("Unknown variable")
                }
            }else println(line)
        }
        line.contains("[-|+]".toRegex()) && line.contains("[a-zA-Z]+".toRegex()) -> {
            var lines = ""
            for (element in line.split(" ")) {
                lines += if (element.matches("[a-zA-Z]+".toRegex())) {
                    "${identifiers[element]!!} "
                } else {
                    "$element "
                }
            }
            addWords.addAll(getDigits(lines))
            addWords.removeLast()
            println(performComputation(addWords))
        }
        else -> {
            addWords.addAll(getDigits(line))
            val addNumbers = performComputation(addWords)
            println(addNumbers)
        }
    }
}

private fun assignVariables(
    newLine: String,
    identifiers: MutableMap<String, Int>
) {
        val lines = newLine.split("=")
        when {
            lines[0].contains("\\d".toRegex()) -> {
                println("Invalid identifier")
            }
            lines[1].contains("[\\d]+".toRegex()) && lines[1].contains("[a-zA-Z]+".toRegex())
                    || lines.size > 2  -> {
                println("Invalid assignment")
            }
            lines[1].matches("[a-zA-Z]+".toRegex()) -> {
                if (identifiers.isNotEmpty() && identifiers.containsKey(lines[1])) {
                    identifiers += lines[0] to identifiers[lines[1]]!!
                } else {
                    println("Unknown variable")
                }
            }
            lines[0].matches("[a-zA-Z]+".toRegex()) && lines[1].matches("[\\d]".toRegex()) -> {
//                identifiers += lines[0] to lines[1].toInt()
                identifiers[lines[0]] = lines[1].toInt()
            }
            else -> println("Invalid identifier")
        }
}

private fun performComputation(
    addWords: MutableList<String>,
): Int {
    var addNumbers1 = 0
        for ((count, i) in addWords.withIndex()) {
            when (i) {
                "-" -> {
                    addNumbers1 -= 2 * (addWords[count + 1].toInt())
                }
                else -> addNumbers1 += i.toInt()
            }
        }
       return addNumbers1
}

private fun getDigits(lined: String): MutableList<String> {
    val addWords = mutableListOf<String>()
    for (i in lined.split(" ")) {
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
    return addWords
}
