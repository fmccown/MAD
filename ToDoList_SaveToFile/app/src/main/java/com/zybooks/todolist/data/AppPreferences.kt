package com.zybooks.todolist.data

enum class TaskOrder(val text: String) {
   NEWEST_IS_LAST("Newest is last"),
   NEWEST_IS_FIRST("Newest is first"),
   ALPHABETIC("Alphabetical")
}

class AppPreferences (
   val taskOrder: TaskOrder = TaskOrder.NEWEST_IS_LAST,
   val confirmDelete: Boolean = true,
   val numTestTasks: Int = 10
)