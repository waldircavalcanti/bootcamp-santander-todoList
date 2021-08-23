package com.walcanty.todolist.repository

import androidx.annotation.WorkerThread
import com.walcanty.todolist.database.dao.TodoListDao
import com.walcanty.todolist.database.model.TodoList
import kotlinx.coroutines.flow.Flow

class TodoListRepository(private val todoListDao: TodoListDao) {

    val allTodoList: Flow<List<TodoList>> = todoListDao.getAll()



    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(todoList: TodoList){
        todoListDao.insert(todoList)
    }


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(todoList: TodoList){
        todoListDao.update(todoList)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(id: Int){
        todoListDao.delete(id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getTaskById(id: Int){
        todoListDao.getTaskById(id)
    }


}