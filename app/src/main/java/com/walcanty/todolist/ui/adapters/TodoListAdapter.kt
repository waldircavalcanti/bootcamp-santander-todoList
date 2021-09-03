package com.walcanty.todolist.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.walcanty.todolist.R
import com.walcanty.todolist.database.model.TodoList
import com.walcanty.todolist.databinding.ItemTaskBinding

class TodoListAdapter : ListAdapter<TodoList, TodoListAdapter.TodoListViewHolder>(DiffCalback()) {

    var listenerEdit : (TodoList) -> Unit = {}
    var listenerDelete : (TodoList) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val binding = ItemTaskBinding.inflate(inflate,parent,false)
        return TodoListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {

        holder.bind(getItem(position))

    }



    inner class TodoListViewHolder(
        private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TodoList) {
            binding.tvTitle.text = item.title
            binding.tvDescription.text = item.description
            binding.tvDate.text = "${item.date} "
            binding.tvHour.text = "${item.hour}"
            binding.ivMore.setOnClickListener {
                showPopup(item)
            }

        }

        private fun showPopup(item: TodoList) {
            val ivMore = binding.ivMore
            val popupMenu = PopupMenu(ivMore.context, ivMore)
            popupMenu.menuInflater.inflate(R.menu.popup_menu,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {

                when(it.itemId){
                    R.id.action_edit -> listenerEdit(item)
                    R.id.action_delete ->listenerDelete(item)
                }
                return@setOnMenuItemClickListener true
            }
            popupMenu.show()
        }


    }

    class DiffCalback : DiffUtil.ItemCallback<TodoList>() {
        override fun areItemsTheSame(oldItem: TodoList, newItem: TodoList): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: TodoList, newItem: TodoList): Boolean {
            return oldItem.id == newItem.id
        }
    }
}