package br.edu.ifsp.scl.tasks.domain.model

data class Task(
    val name: String,
    val writer: String,
    val status: String,
    val synopsis: String? = "",
    var id: Int = 0,
)
