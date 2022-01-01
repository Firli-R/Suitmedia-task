package com.example.task_suitmedia.model

data class UserData (
    val page: Int,
    val total_pages: Int,
    val data : ArrayList<User>
)