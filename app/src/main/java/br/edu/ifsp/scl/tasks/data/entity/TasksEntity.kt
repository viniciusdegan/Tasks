package br.edu.ifsp.scl.tasks.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val writer: String,
    val synopsis: String?,
    val status: String
)
