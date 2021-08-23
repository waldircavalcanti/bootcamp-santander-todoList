package com.walcanty.todolist.ui

import androidx.lifecycle.*
import com.walcanty.todolist.database.model.TodoList
import com.walcanty.todolist.repository.TodoListRepository
import kotlinx.coroutines.launch

class TodoListViewModel(private val repository: TodoListRepository) : ViewModel() {


    val allTasks: LiveData<List<TodoList>> = repository.allTodoList.asLiveData()

    /**
     * Lançamento de uma nova co-rotina para inserir os dados de forma não bloqueadora
     */
    fun insert(todoList: TodoList) = viewModelScope.launch {
        repository.insert(todoList)
    }

    fun update(todoList: TodoList) = viewModelScope.launch {
        repository.update(todoList)
    }

    fun delete(id: Int) = viewModelScope.launch {
        repository.delete(id)
    }

    fun getTaskById(id: Int) = viewModelScope.launch {
        repository.getTaskById(id)
    }




}

class TodoListViewModelFactory(private val repository: TodoListRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodoListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TodoListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}