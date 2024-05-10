package com.example.taskmngmtapp

import TaskAdapter
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmngmtapp.databinding.ActivityMainBinding
import com.example.taskmngmtapp.listeners.OnItemClickListener
import com.example.taskmngmtapp.models.Tasks
import com.example.taskmngmtapp.utils.setupDialog
import com.example.taskmngmtapp.viewmodels.TaskViewModel
import java.util.Date

class MainActivity : AppCompatActivity(), OnItemClickListener {
    private val mainBinding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val addTaskDialog: Dialog by lazy {
        Dialog(this, R.style.DialogCustomTheme).apply {
            setupDialog(R.layout.add_task)
        }
    }
    private val updateTaskDialog: Dialog by lazy {
        Dialog(this, R.style.DialogCustomTheme).apply {
            setupDialog(R.layout.update_task)
        }
    }
    private lateinit var taskAdapter: TaskAdapter
    private val taskViewModel: TaskViewModel by lazy { ViewModelProvider(this).get(TaskViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainBinding.root)

        // Initialize the adapter
        taskAdapter = TaskAdapter(this)

        // Set the adapter and layout manager for the RecyclerView
        val taskRecyclerView = findViewById<RecyclerView>(R.id.taskRecyclerView)
        taskRecyclerView.adapter = taskAdapter
        taskRecyclerView.layoutManager = LinearLayoutManager(this)

        // Observe the tasks from the ViewModel and update the adapter
        taskViewModel.tasks.observe(this, Observer { tasks ->
            taskAdapter.setTasks(tasks)
        })

        // Update task
        val updateCloseImage = updateTaskDialog.findViewById<ImageView>(R.id.closeImage)
        updateCloseImage.setOnClickListener { updateTaskDialog.dismiss() }

        // Add task
        val addCloseImg = addTaskDialog.findViewById<ImageView>(R.id.closeImage)
        addCloseImg.setOnClickListener { addTaskDialog.dismiss() }

        // Set up click listener for adding a task
        mainBinding.addTaskBtn.setOnClickListener {
            addTaskDialog.show()
        }

        // Set up click listener for save button
        val saveButton = addTaskDialog.findViewById<Button>(R.id.saveTaskBtn)
        saveButton.setOnClickListener {
            // Retrieve task title and description from user inputs
            val taskTitleInput = addTaskDialog.findViewById<EditText>(R.id.TaskTi)
            val taskDescriptionInput = addTaskDialog.findViewById<EditText>(R.id.TaskDesc)

            val taskTitle = taskTitleInput.text.toString().trim()
            val taskDescription = taskDescriptionInput.text.toString().trim()

            // Check if the title and description are not empty
            if (taskTitle.isNotEmpty() && taskDescription.isNotEmpty()) {
                // Create a new task object with the retrieved values
                val currentDate = Date() // Current date
                val newTask = Tasks(null, taskTitle, taskDescription, currentDate)

                // Add the new task to the ViewModel
                taskViewModel.insertTask(newTask)

                // Dismiss the dialog
                addTaskDialog.dismiss()
            } else {
                // Show an error message if either the title or description is empty
                Toast.makeText(this@MainActivity, "Please enter both title and description", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onItemClick(task: Tasks) {
        // Handle item click event
    }

    override fun onEditClick(task: Tasks) {
        // Show the update task dialog and populate the form with the task data
        showUpdateTaskDialog(task)
    }

    override fun onDeleteClick(task: Tasks) {
        // Show a confirmation dialog to delete the task
        showDeleteConfirmationDialog(task)
    }

    private fun showUpdateTaskDialog(task: Tasks) {
        // Show the update task dialog and populate the form with the task data
        updateTaskDialog.show()
        val taskTitleInput = updateTaskDialog.findViewById<EditText>(R.id.editTask)
        val taskDescriptionInput = updateTaskDialog.findViewById<EditText>(R.id.editTaskDesc)
        taskTitleInput.setText(task.title)
        taskDescriptionInput.setText(task.description)

        // Set up click listener for the update button
        val updateButton = updateTaskDialog.findViewById<Button>(R.id.updateTaskBtn)
        updateButton.setOnClickListener {
            val updatedTitle = taskTitleInput.text.toString().trim()
            val updatedDescription = taskDescriptionInput.text.toString().trim()

            if (updatedTitle.isNotEmpty() && updatedDescription.isNotEmpty()) {
                val updatedTask = Tasks(task.id, updatedTitle, updatedDescription, task.date)
                taskViewModel.updateTask(updatedTask)
                updateTaskDialog.dismiss()
            } else {
                Toast.makeText(this@MainActivity, "Please enter both title and description", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showDeleteConfirmationDialog(task: Tasks) {
        // Show a confirmation dialog to delete the task
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete Task")
        builder.setMessage("Are you sure you want to delete this task?")

        builder.setPositiveButton("Delete") { _, _ ->
            taskViewModel.deleteTask(task)
        }

        builder.setNegativeButton("Cancel", null)

        val dialog = builder.create()
        dialog.show()
    }
}