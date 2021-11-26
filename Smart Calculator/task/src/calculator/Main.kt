package calculator

val stack = Stack()
val postFixExpressions = mutableListOf<String>()
val digitsMap = mutableMapOf<String, Int>()

fun main() {
    while (true) {
        val input = readLine()!!
        when {
            input.isBlank() -> continue
            input[0] == '/' -> {
                if (commands(input)) {
                    digitsMap.clear()
                    break
                }
            }
            input.contains("(") && !input.contains(")")
                    || input.contains(")") && !input.contains("(")
                    || input.contains("[*]{2,}".toRegex())
                    || input.contains("[/]{2,}".toRegex()) -> {
                println("Invalid expression")
            }
            else -> {
                try {
                    val formattedString = input.reformatString()
                    decompile(formattedString)
                    compute(postFixExpressions)
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
            println("This program is a basic calculator that allows for storing of values as variables")
        }
        else -> {
            println("Unknown command")
        }
    }
    return false
}

fun String.reformatString(): List<String> {
    var input = this
    input = input.replace("[+]+".toRegex(), "+")
    input = input.replace("---", "-")
    input = input.replace("--", "+")
    input = input.replace("----", "+")
    input = input.replace("------", "+")
    input = input.replace("-----", "-")
    for (w in listOf("+", "=", "*", "/", "(", ")")) {
        input = input.replace(w, " $w ")
    }
    input = input.replace("\\s+".toRegex(), " ").trim()
    return input.split(" ")
}

fun String.operandToInt(): Int = if (this.isDigit()) this.toInt() else digitsMap[this] ?: 0

fun String.isDigit() = this.matches("[-+]?[0-9]+\\b".toRegex())

fun String.isKey() = this.matches("[a-zA-Z]+\\b".toRegex())

fun assign(k: String, v: String) {
    val isKeyValid = k.isKey()
    val isValueValid = v.isKey()
    val isValueNumber = v.isDigit()

    if (!isKeyValid) {
        println("Invalid identifier")
    }
    if (!isValueNumber) { // not number
        if (!isValueValid) {
            println("Invalid assignment")
        }
        if (!digitsMap.containsKey(v)) {
            println("Unknown variable")
        }
    }
    digitsMap[k] = if (isValueNumber) v.toInt() else digitsMap[v] ?: 0

}

fun compute(list: List<String>) {
    var result = 0
    var isCalc = false
    var isAss = false
    val operands = mutableListOf<String>()
    for (w in list) {
        if (w in listOf("+", "-", "*", "/", "=")) {
            isCalc = true
            val operand1 = operands[operands.lastIndex - 1]
            val operand2 = operands[operands.lastIndex]
            when (w) {
                "+" -> result = operand1.operandToInt() + operand2.operandToInt()
                "-" -> result = operand1.operandToInt() - operand2.operandToInt()
                "*" -> result = operand1.operandToInt() * operand2.operandToInt()
                "/" -> result = operand1.operandToInt() / operand2.operandToInt()
                "=" -> {
                    isAss = true
                    assign(operand1, operand2)
                }
            }
            operands.removeAt(operands.lastIndex)
            operands[operands.lastIndex] = result.toString()
        } else {
            operands.add(w)
        }
    }
    if (!isCalc) {
        if (digitsMap.containsKey(operands.last())) {
            result = digitsMap[operands.last()] ?: 0
        } else {
            println("Unknown variable")
            return
        }
    }
    if (!isAss) println(result)
}

fun decompile(list: List<String>) {
    postFixExpressions.clear()
    for (w in list) {
        when (w) {
            "(" -> stack.push(w)
            ")" -> while (!stack.isEmpty()) {
                if (stack.peek() != "(") {
                    val st = stack.pop().toString()
                    postFixExpressions.add(st)
                } else {
                    stack.pop()
                    break
                }
            }
            "+" -> pushLow(w)
            "-" -> pushLow(w)
            "*" -> pushTop(w)
            "/" -> pushTop(w)
            "=" -> pushTop(w)
            else -> postFixExpressions.add(w)
        }
    }
    while (!stack.isEmpty()) {
        postFixExpressions.add(stack.pop().toString())
    }
}

fun pushTop(w: String) {
    while (stack.isNotEmpty() && stack.peek() == "[/|*]".toRegex()) {
        val st = stack.pop().toString()
        postFixExpressions.add(st)
    }
    stack.size
    stack.push(w)
}

fun pushLow(w: String) {
    while (!stack.isEmpty() && stack.peek() != "(") {
        val st = stack.pop().toString()
        postFixExpressions.add(st)
    }
    stack.push(w)
}

class Stack {
    private val elements: MutableList<Any> = mutableListOf()

    fun isEmpty() = elements.isEmpty()

    fun isNotEmpty() = elements.isNotEmpty()

    val size = elements.size

    fun push(item: Any) = elements.add(item)

    fun pop(): Any? {
        val item = elements.lastOrNull()
        if (!isEmpty()) {
            elements.removeAt(elements.size - 1)
        }
        return item
    }

    fun peek(): Any = elements.last()

    override fun toString(): String = elements.toString()
}