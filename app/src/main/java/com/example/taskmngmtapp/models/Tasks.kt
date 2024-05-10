package com.example.taskmngmtapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Tasks(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "taskId")
    val id: Long? = null, // Allow Room to auto-generate the ID
    @ColumnInfo(name = "taskTitle")
    val title: String,
    val description: String,
    val date: Date
)
