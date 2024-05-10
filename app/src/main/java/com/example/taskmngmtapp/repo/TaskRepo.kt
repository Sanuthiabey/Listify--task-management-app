package com.example.taskmngmtapp.repo

import android.app.Application
import com.example.taskmngmtapp.dao.TaskDao
import com.example.taskmngmtapp.database.TaskDatabase
import com.example.taskmngmtapp.models.Tasks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskRepo(private val application: Application) {
    private val taskDao: TaskDao

    init {
        val database = TaskDatabase.getInstance(application)
        taskDao = database.taskDao()
    }

    // Function to insert a new task asynchronously
    suspend fun insertTask(task: Tasks) {
        withContext(Dispatchers.IO) {
            taskDao.insertTask(task)
        }
    }
    suspend fun updateTask(task: Tasks) {
        withContext(Dispatchers.IO) {
            taskDao.updateTask(task)
        }
    }

    suspend fun deleteTask(task: Tasks) {
        withContext(Dispatchers.IO) {
            taskDao.deleteTask(task)
        }
    }
    // Function to get all tasks asynchronously
    suspend fun getAllTasks(): List<Tasks> {
        return withContext(Dispatchers.IO) {
            taskDao.getAllTasks().toList() // Convert to Kotlin's List
        }
    }

}