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



//    private class WordDatabaseCallback(
//        private val scope: CoroutineScope
//    ) : RoomDatabase.Callback() {
//
//        override fun onCreate(db: SupportSQLiteDatabase) {
//            super.onCreate(db)
//            INSTANCE?.let { database ->
//                scope.launch {
//                    populateDataBase(database.todoListDao())
//                    // TODO: Add your own words!
//
//                }
//            }
//        }
//
//        suspend fun populateDataBase(todoListDao: TodoListDao) {
//            todoListDao.deleteAll()
//
//            var todoList = TodoList(
//                0,"Título Tarefa",
//                "Descrição",
//                "hh:mm",
//                "dd/mm/ano")
//            todoListDao.insert(todoList)
//
//        }
//    }

    companion object{
        //Singleton evita múltiplas instâncias de abertura de banco de dados no mesmo tempo
        @Volatile
        private var INSTANCE: TodoListDatabase? = null

        fun getDatabase(context: Context,scope: CoroutineScope):TodoListDatabase{
            //Se a INSTANCE não for nula, então retorne-a, se for, crie o banco de dados
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