package com.walcanty.todolist.ui.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.walcanty.todolist.R
import com.walcanty.todolist.database.model.TodoList
import com.walcanty.todolist.databinding.ActivityAddTaskBinding
import com.walcanty.todolist.extensions.format
import com.walcanty.todolist.extensions.text
import java.util.*

class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding
    private var updateId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        insertListeners()


        if(intent.hasExtra(TASK_ID)){
            binding.toolBar.title = getString(R.string.label_edit)
            binding.btnNewTask.text = getString(R.string.label_update)
           val task = intent.getParcelableExtra<TodoList>(TASK_ID)
            if (task != null) {
                updateId = task.id
                binding.tilTitle.text = task.title
                binding.tilDescription.text = task.description
                binding.tilDate.text = task.date
                binding.tilHour.text = task.hour
            }
            Log.e("TAG","listenerUpdate $task.")

        }

    }




    private fun insertListeners() {
        binding.tilDate.editText?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.addOnPositiveButtonClickListener {
                val timeZone = TimeZone.getDefault()
                val offset = timeZone.getOffset(Date().time) * -1
                binding.tilDate.text = Date(it + offset).format()
            }
            datePicker.show(supportFragmentManager, "DATE_PICKER_TAG")
        }


        binding.tilHour.editText?.setOnClickListener {
            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()
            timePicker.addOnPositiveButtonClickListener {
                val minute =
                    if (timePicker.minute in 0..9) "0${timePicker.minute}" else timePicker.minute
                val hour = if (timePicker.hour in 0..9) "0${timePicker.hour}" else timePicker.hour
                binding.tilHour.text = "$hour:$minute"
            }

            timePicker.show(supportFragmentManager, null)
        }


        binding.btnCancel.setOnClickListener {
            finish()
        }

        binding.toolBar.setNavigationOnClickListener {
            finish()
        }


        binding.btnNewTask.setOnClickListener {

                val replyIntent = Intent()
                if (TextUtils.isEmpty(binding.tilTitle.text)) {
                    binding.tilTitle.error = "Digite"
                    setResult(Activity.RESULT_CANCELED, replyIntent)
                } else {
                    val task = TodoList(
                        id = updateId,
                        title = binding.tilTitle.text,
                        description = binding.tilDescription.text,
                        date = binding.tilDate.text,
                        hour = binding.tilHour.text
                    )
                    replyIntent.putExtra("REPLY", task)

                    setResult(Activity.RESULT_OK, replyIntent)

                    finish()
                }

        }

    }

    companion object {
        const val TASK_ID = "task_id"
    }

}