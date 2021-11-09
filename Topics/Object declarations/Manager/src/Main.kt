class Task(val name: String)

object Manager {
    var solvedTask = 0
    fun solveTask (task: Task) {
        println("Task ${task.name} solved!")
        solvedTask++
    }
}