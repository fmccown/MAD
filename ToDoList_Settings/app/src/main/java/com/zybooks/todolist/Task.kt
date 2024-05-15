package com.zybooks.todolist

import java.util.UUID

data class Task (
   var id: Int = 1,
   var body: String = "",
   var completed: Boolean = false
)