package de.hhn.a9persistenzunddatenbank_anbindung.ui.screen

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import de.hhn.a9persistenzunddatenbank_anbindung.controller.ToDoController

@Composable
fun DashboardScreen(context: Context) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "dashboard") {
        composable("dashboard") {
            val toDoController = ToDoController(context)
            var activeToDos by remember { mutableStateOf(toDoController.getToDos(0).sortedBy { it.priority }) }
            var completedToDos by remember { mutableStateOf(toDoController.getToDos(1).sortedBy { it.priority }) }

            Scaffold(
                floatingActionButton = {
                    FloatingActionButton(onClick = { navController.navigate("add_edit_todo") }) {
                        Icon(Icons.Default.Add, contentDescription = "Add ToDo")
                    }
                }
            ) { paddingValues ->
                LazyColumn(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                ) {
                    item {
                        Text(
                            "Active ToDos",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    if (activeToDos.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("No active todos. Add one now!", style = MaterialTheme.typography.bodyLarge)
                            }
                        }
                    } else {
                        items(activeToDos) { todo ->
                            ToDoCard(
                                todo = todo,
                                onComplete = {
                                    toDoController.updateToDo(todo.copy(status = 1))
                                    activeToDos = toDoController.getToDos(0).sortedBy { it.priority }
                                    completedToDos = toDoController.getToDos(1).sortedBy { it.priority }
                                },
                                onEdit = {
                                    navController.navigate("add_edit_todo?id=${todo.id}")
                                },
                                onDelete = {
                                    toDoController.deleteToDo(todo.id)
                                    activeToDos = toDoController.getToDos(0).sortedBy { it.priority }
                                }
                            )
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            "Completed ToDos",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    if (completedToDos.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("No completed todos yet.", style = MaterialTheme.typography.bodyLarge)
                            }
                        }
                    } else {
                        items(completedToDos) { todo ->
                            ToDoCard(
                                todo = todo,
                                onComplete = {
                                    val newStatus = if (todo.status == 1) 0 else 1
                                    toDoController.updateToDo(todo.copy(status = newStatus))
                                    activeToDos = toDoController.getToDos(0).sortedBy { it.priority }
                                    completedToDos = toDoController.getToDos(1).sortedBy { it.priority }
                                },
                                onEdit = {
                                    navController.navigate("add_edit_todo?id=${todo.id}")
                                },
                                onDelete = {
                                    toDoController.deleteToDo(todo.id)
                                    completedToDos = toDoController.getToDos(1).sortedBy { it.priority }
                                }
                            )
                        }
                    }
                }
            }
        }

        composable("add_edit_todo?id={id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
            val todo = id?.let { ToDoController(context).getToDoById(it) }
            AddEditToDoScreen(context, navController, todo)
        }
    }
}