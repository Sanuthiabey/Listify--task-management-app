package com.example.taskmngmtapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.taskmngmtapp.models.Tasks
import com.example.taskmngmtapp.repo.TaskRepo
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val taskRepository = TaskRepo(application)
    private val _tasks = MutableLiveData<List<Tasks>>()
    val tasks: LiveData<List<Tasks>> = _tasks

    fun insertTask(task: Tasks) {
        viewModelScope.launch {
            try {
                taskRepository.insertTask(task)
                _tasks.value = taskRepository.getAllTasks()
            } catch (e: Exception) {
                // Handle exceptions (e.g., database errors) appropriately
                // Log or display an error message
            }
        }
    }

    fun updateTask(task: Tasks) {
        viewModelScope.launch {
            try {
                taskRepository.updateTask(task)
                _tasks.value = taskRepository.getAllTasks()
            } catch (e: Exception) {
                // Handle exceptions (e.g., database errors) appropriately
                // Log or display an error message
            }
        }
    }

    fun deleteTask(task: Tasks) {
        viewModelScope.launch {
            try {
                taskRepository.deleteTask(task)
                _tasks.value = taskRepository.getAllTasks()
            } catch (e: Exception) {
                // Handle exceptions (e.g., database errors) appropriately
                // Log or display an error message
            }
        }
    }
}