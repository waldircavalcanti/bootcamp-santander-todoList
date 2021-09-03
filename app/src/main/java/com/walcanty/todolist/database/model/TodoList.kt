package com.walcanty.todolist.database.model


import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "todolist_database")
data class TodoList(
    @PrimaryKey(autoGenerate = true) var id:Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description")val description: String,
    @ColumnInfo(name = "hour")val hour: String,
    @ColumnInfo(name = "date")val date: String

): Parcelable