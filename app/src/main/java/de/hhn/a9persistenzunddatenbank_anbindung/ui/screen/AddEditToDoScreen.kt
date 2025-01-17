package de.hhn.a9persistenzunddatenbank_anbindung.ui.screen

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import de.hhn.a9persistenzunddatenbank_anbindung.controller.ToDoController
import de.hhn.a9persistenzunddatenbank_anbindung.model.ToDo
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddEditToDoScreen(context: Context, navController: NavController, todo: ToDo? = null) {
    val toDoController = ToDoController(context)
    var name by remember { mutableStateOf(todo?.name ?: "") }
    var description by remember { mutableStateOf(todo?.description ?: "") }
    var priority by remember { mutableStateOf(todo?.priority?.toString() ?: "") }
    var deadline by remember { mutableStateOf(todo?.deadline ?: "") }
    var showDatePicker by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Scaffold {
        Column(modifier = Modifier.padding(16.dp)) {
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                isError = name.isEmpty(),
                modifier = Modifier.fillMaxWidth()
            )
            if (name.isEmpty()) {
                Text("Name is required", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description (optional)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = priority,
                onValueChange = { priority = it.filter { char -> char.isDigit() } },
                label = { Text("Priority") },
                isError = priority.isEmpty(),
                modifier = Modifier.fillMaxWidth()
            )
            if (priority.isEmpty()) {
                Text("Priority is required", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }
            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = if (deadline.isEmpty()) "No deadline selected" else "Deadline: $deadline",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { showDatePicker = true }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Select deadline"
                    )
                }
            }
            if (deadline.isEmpty()) {
                Text("Deadline is required", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }

            if (showDatePicker) {
                DatePickerDialog(
                    onDateSelected = {
                        deadline = it
                        showDatePicker = false
                    },
                    onDismiss = { showDatePicker = false }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                if (name.isNotEmpty() && priority.isNotEmpty() && deadline.isNotEmpty()) {
                    val updatedToDo = ToDo(
                        id = todo?.id ?: 0,
                        name = name,
                        description = description,
                        priority = priority.toInt(),
                        deadline = deadline,
                        status = todo?.status ?: 0
                    )
                    if (todo == null) {
                        toDoController.insertToDo(updatedToDo)
                    } else {
                        toDoController.updateToDo(updatedToDo)
                    }
                    navController.navigate("dashboard") { popUpTo("dashboard") { inclusive = true } }
                } else {
                    errorMessage = "Please fill out all required fields."
                }
            }) {
                Text(if (todo == null) "Save" else "Update")
            }

            if (errorMessage != null) {
                Text(
                    text = errorMessage!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(onDateSelected: (String) -> Unit, onDismiss: () -> Unit) {
    val datePickerState = rememberDatePickerState()
    val dateFormatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 4.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DatePicker(
                    state = datePickerState,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel")
                    }
                    TextButton(onClick = {
                        val selectedMillis = datePickerState.selectedDateMillis
                        if (selectedMillis != null) {
                            val selectedDate = dateFormatter.format(Date(selectedMillis))
                            onDateSelected(selectedDate)
                        } else {
                            onDismiss()
                        }
                    }) {
                        Text("OK")
                    }
                }
            }
        }
    }
}