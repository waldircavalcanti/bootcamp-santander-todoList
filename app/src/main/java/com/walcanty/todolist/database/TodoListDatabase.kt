package com.walcanty.todolist.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.walcanty.todolist.database.dao.TodoListDao
import com.walcanty.todolist.database.model.TodoList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [TodoList::class], version = 1, exportSchema = false)
abstract class TodoListDatabase : RoomDatabase() {

    abstract fun todoListDao():TodoListDao





    companion object{

        @Volatile
        private var INSTANCE: TodoListDatabase? = null

        fun getDatabase(context: Context,scope: CoroutineScope):TodoListDatabase{

            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TodoListDatabase::class.java,
                    "todolist_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}