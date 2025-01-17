package de.hhn.a9persistenzunddatenbank_anbindung.model

data class ToDo(
    val id: Int = 0,
    val name: String,
    val description: String?,
    val priority: Int,
    val deadline: String,
    val status: Int
)