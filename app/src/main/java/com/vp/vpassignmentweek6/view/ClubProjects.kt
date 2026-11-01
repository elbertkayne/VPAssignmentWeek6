package com.vp.vpassignmentweek6.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vp.vpassignmentweek6.viewmodel.CampusViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClubProjects(
    viewModel: CampusViewModel,
    clubId: String,
    onBackClick: () -> Unit,
    onNavigateToProjectDetail: (String) -> Unit
) {
    val club = viewModel.communities.find { it.id == clubId }
    val clubProjects = viewModel.projects.filter { it.clubID == clubId }
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("${club?.name ?: "Club"} Projects") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add Project")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(club?.name ?: "", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text("${club?.memberCount} Anggota • ${clubProjects.size} Proyek Aktif", fontSize = 12.sp, color = Color.Gray)
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text("Daftar Proyek Mahasiswa (${clubProjects.size})", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }

            items(clubProjects) { project ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onNavigateToProjectDetail(project.id) }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(project.title, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(project.description, fontSize = 12.sp, color = Color.Gray, maxLines = 2)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Status: ${project.status}", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF1E3C72))
                        }
                        IconButton(onClick = { viewModel.deleteProject(project.id) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
                        }
                    }
                }
            }
        }

        if (showAddDialog) {
            var title by remember { mutableStateOf("") }
            var description by remember { mutableStateOf("") }
            val availableTechStack = listOf("Kotlin", "Jetpack Compose", "Firebase", "Ktor", "Room", "Python", "Google Maps SDK")
            val selectedTechStack = remember { mutableStateListOf<String>() }
            val isFormValid = title.isNotBlank() && description.isNotBlank() && selectedTechStack.isNotEmpty()

            AlertDialog(
                onDismissRequest = { },
                title = { Text("Add Project (${club?.name})") },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = title,
                            onValueChange = { title = it },
                            label = { Text("Judul Proyek *") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = description,
                            onValueChange = { description = it },
                            label = { Text("Deskripsi Proyek (Minimal 3 Baris) *") },
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 3
                        )
                        Text("Tech Stack (Pilihan Multi-Selection):", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        availableTechStack.chunked(3).forEach { rowItems ->
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                rowItems.forEach { tech ->
                                    val isSelected = selectedTechStack.contains(tech)
                                    FilterChip(
                                        selected = isSelected,
                                        onClick = {
                                            if (isSelected) selectedTechStack.remove(tech) else selectedTechStack.add(tech)
                                        },
                                        label = { Text(tech, fontSize = 10.sp) }
                                    )
                                }
                            }
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.addProject(clubId, title, description, selectedTechStack.toList())
                            showAddDialog = false
                        },
                        enabled = isFormValid
                    ) {
                        Text("Save")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showAddDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}

