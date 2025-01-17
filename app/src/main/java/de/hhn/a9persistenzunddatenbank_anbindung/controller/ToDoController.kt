package de.hhn.a9persistenzunddatenbank_anbindung.controller

import android.content.ContentValues
import android.content.Context
import de.hhn.a9persistenzunddatenbank_anbindung.database.DbHelper
import de.hhn.a9persistenzunddatenbank_anbindung.model.ToDo

class ToDoController(context: Context) {
    private val dbHelper = DbHelper(context)

    fun insertToDo(todo: ToDo): Boolean {
        val db = dbHelper.writableDatabase
        return try {
            val values = ContentValues().apply {
                put("name", todo.name)
                put("description", todo.description)
                put("priority", todo.priority)
                put("deadline", todo.deadline)
                put("status", todo.status)
            }
            val result = db.insert("todos", null, values)
            result != -1L
        } finally {
            db.close()
        }
    }

    fun updateToDo(todo: ToDo): Boolean {
        val db = dbHelper.writableDatabase
        return try {
            val values = ContentValues().apply {
                put("name", todo.name)
                put("description", todo.description)
                put("priority", todo.priority)
                put("deadline", todo.deadline)
                put("status", todo.status)
            }
            db.update("todos", values, "id = ?", arrayOf(todo.id.toString())) > 0
        } finally {
            db.close()
        }
    }

    fun deleteToDo(id: Int): Boolean {
        val db = dbHelper.writableDatabase
        return try {
            db.delete("todos", "id = ?", arrayOf(id.toString())) > 0
        } finally {
            db.close()
        }
    }

    fun getToDos(status: Int): List<ToDo> {
        val db = dbHelper.readableDatabase
        val todos = mutableListOf<ToDo>()
        val cursor = db.rawQuery("SELECT * FROM todos WHERE status = ?", arrayOf(status.toString()))
        try {
            if (cursor.moveToFirst()) {
                do {
                    val todo = ToDo(
                        id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        name = cursor.getString(cursor.getColumnIndexOrThrow("name")),
                        description = cursor.getString(cursor.getColumnIndexOrThrow("description")),
                        priority = cursor.getInt(cursor.getColumnIndexOrThrow("priority")),
                        deadline = cursor.getString(cursor.getColumnIndexOrThrow("deadline")),
                        status = cursor.getInt(cursor.getColumnIndexOrThrow("status"))
                    )
                    todos.add(todo)
                } while (cursor.moveToNext())
            }
        } finally {
            cursor.close()
            db.close()
        }
        return todos
    }

    fun getToDoById(id: Int): ToDo? {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM todos WHERE id = ?", arrayOf(id.toString()))
        return try {
            if (cursor.moveToFirst()) {
                ToDo(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    name = cursor.getString(cursor.getColumnIndexOrThrow("name")),
                    description = cursor.getString(cursor.getColumnIndexOrThrow("description")),
                    priority = cursor.getInt(cursor.getColumnIndexOrThrow("priority")),
                    deadline = cursor.getString(cursor.getColumnIndexOrThrow("deadline")),
                    status = cursor.getInt(cursor.getColumnIndexOrThrow("status"))
                )
            } else {
                null
            }
        } finally {
            cursor.close()
            db.close()
        }
    }
}