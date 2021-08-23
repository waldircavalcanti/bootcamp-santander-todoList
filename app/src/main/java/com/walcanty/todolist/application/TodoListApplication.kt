package com.walcanty.todolist.application

import android.app.Application
import com.walcanty.todolist.database.TodoListDatabase
import com.walcanty.todolist.repository.TodoListRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class TodoListApplication : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())


    val database by lazy { TodoListDatabase.getDatabase(this,applicationScope) }
    val repository by lazy { TodoListRepository(database.todoListDao()) }
}