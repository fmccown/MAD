package com.zybooks.todolist

data class Task (
   var id: Int = 1,
   var body: String = "",
   var completed: Boolean = false
)