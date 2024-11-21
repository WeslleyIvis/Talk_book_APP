package com.example.starteraplication.model

data class User (
    val id : Int? = null,
    val name: String = "",
    val email: String = "",
    val age: Int = 0,
    val photoUrl: String = "",
    val password: String = ""
)
