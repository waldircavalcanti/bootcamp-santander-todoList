package com.walcanty.todolist

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.walcanty.todolist.application.TodoListApplication
import com.walcanty.todolist.database.model.TodoList
import com.walcanty.todolist.databinding.ActivityMainBinding
import com.walcanty.todolist.ui.TodoListViewModel
import com.walcanty.todolist.ui.TodoListViewModelFactory
import com.walcanty.todolist.ui.adapters.TodoListAdapter
import com.walcanty.todolist.ui.view.AddTaskActivity


class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TodoListAdapter
    private lateinit var binding: ActivityMainBinding


    private val taskViewModel: TodoListViewModel by viewModels {
        TodoListViewModelFactory((application as TodoListApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.rvTasks
        adapter = TodoListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = StaggeredGridLayoutManager( 2,StaggeredGridLayoutManager.VERTICAL)


        val resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.let { it ->
                    val reply = it.getParcelableExtra<TodoList>("REPLY")?.let {
                        taskViewModel.insert(it)
                        Toast.makeText(
                            this,"Task entered successfully",Toast.LENGTH_SHORT).show()
                        Log.e("TAG", "Insert Banco ")
                    }

                }

            }
        }


        val updateTask = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.let { it ->
                    val reply = it.getParcelableExtra<TodoList>("REPLY")?.let {
                        taskViewModel.update(it)
                        Toast.makeText(
                            this,"Task deleted successfully",Toast.LENGTH_SHORT).show()
                        Log.e("TAG", "Insert Banco $it")
                    }
                }

            }
        }



        binding.fab.setOnClickListener {
            resultLauncher.launch(Intent(this, AddTaskActivity::class.java))
        }

        adapter.listenerEdit = {
            val editTaskIntent = Intent(this, AddTaskActivity::class.java)
            editTaskIntent.putExtra(AddTaskActivity.TASK_ID, it)
            updateTask.launch(editTaskIntent)

            Log.e("TAG", "listenerEdit $it")
        }

        adapter.listenerDelete = {
            taskViewModel.delete(it.id)
            Log.e("TAG", "listenerDelete $it")
        }


    }



    override fun onStart() {
        super.onStart()

        //Observer
        taskViewModel.allTasks.observe(this, Observer { tasks ->

            tasks?.let {
                binding.includeEmpty.emptyState.visibility = if (it.isEmpty()) View.VISIBLE
                else View.GONE
                adapter.submitList(it)
            }
        })
    }


}