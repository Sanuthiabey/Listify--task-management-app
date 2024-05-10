package com.example.taskmngmtapp.listeners

import com.example.taskmngmtapp.models.Tasks

interface OnItemClickListener {
    fun onItemClick(task: Tasks)
    fun onEditClick(task: Tasks)
    fun onDeleteClick(task: Tasks)
}
