package com.example.taskmngmtapp.dao

import androidx.room.*
import com.example.taskmngmtapp.models.Tasks
import java.util.List;
import androidx.room.Insert
import androidx.room.Query
@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTask(task: Tasks): Long

    @Update
    fun updateTask(task: Tasks)

    @Delete
    fun deleteTask(task: Tasks)
    @Query("SELECT * FROM tasks")
    fun getAllTasks(): List<Tasks>
}
