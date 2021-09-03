package com.walcanty.todolist.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.walcanty.todolist.database.model.TodoList
import kotlinx.coroutines.flow.Flow


@Dao
interface TodoListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(todoList: TodoList): Long

    @Update
    suspend fun update(todoList: TodoList)

    @Query("DELETE FROM todolist_database WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("DELETE FROM todolist_database")
    suspend fun deleteAll()

    @Query("SELECT * FROM todolist_database ORDER BY substr(date,7,2) || substr(date,4,2) || substr(date,1,2)")
    fun getAll(): Flow<List<TodoList>>

    @Query("SELECT * FROM todolist_database WHERE id = :id")
    fun getTaskById(id: Int): Flow<List<TodoList>>
}