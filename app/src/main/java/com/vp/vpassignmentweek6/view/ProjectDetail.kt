package com.vp.vpassignmentweek6.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vp.vpassignmentweek6.viewmodel.CampusViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectDetail(
    viewModel: CampusViewModel,
    projectId: String,
    onBackClick: () -> Unit
) {
    val project = viewModel.projects.find { it.id == projectId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Project Detail") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        if (project == null) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text("Proyek tidak ditemukan.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Text(project.clubName, fontSize = 12.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                    Text(project.title, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                }

                item {
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("Mark as Done", fontWeight = FontWeight.Bold)
                                Text("Status: ${project.status}", fontSize = 12.sp, color = Color.Gray)
                            }
                            Switch(
                                checked = project.status == "Completed",
                                onCheckedChange = { isChecked ->
                                    val newStatus = if (isChecked) "Completed" else "In Progress"
                                    viewModel.updateProjectStatus(project.id, newStatus)
                                }
                            )
                        }
                    }
                }

                item {
                    Text("Deskripsi Lengkap Proyek", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(project.description, fontSize = 14.sp, color = Color.DarkGray)
                }

                item {
                    Text("Tech Stack Terpilih", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        project.techStack.forEach { tech ->
                            AssistChip(onClick = {}, label = { Text(tech) })
                        }
                    }
                }
            }
        }
    }
}