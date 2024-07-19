package com.zybooks.heartcats.data

import kotlinx.serialization.Serializable

@Serializable
data class Cat(
   val id: String,
   val url: String,
   val width: Int,
   val height: Int
)