package com.vp.vpassignmentweek6.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vp.vpassignmentweek6.viewmodel.CampusViewModel

@Composable
fun Dashboard(viewModel: CampusViewModel, onNavigateToClub: (String) -> Unit) {
    val joinedClubs = viewModel.communities.filter { it.isJoined }
    val studentProjects = viewModel.projects

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text("CampusTech Hub", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text("Student Profile & Portfolio Dashboard", fontSize = 12.sp, color = Color.Gray)
        }

        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Brush.horizontalGradient(listOf(Color(0xFF1E3C72), Color(0xFF2A5298))))
                    .padding(20.dp)
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("AP", fontWeight = FontWeight.Bold, color = Color(0xFF1E3C72))
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text("MAHASISWA AKTIF", fontSize = 10.sp, color = Color.LightGray)
                            Text("Aditya Pratama", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            Text("Teknik Informatika • Angkatan 2022\nNIM: 22001045", fontSize = 11.sp, color = Color.White.copy(alpha = 0.8f))
                        }
                    }
                }
            }
        }

        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Card(modifier = Modifier.weight(1f)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("${viewModel.joinedClubs}", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        Text("Joined Clubs", fontSize = 12.sp, color = Color.Gray)
                    }
                }
                Card(modifier = Modifier.weight(1f)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("${viewModel.totalProjects}", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        Text("Total Projects", fontSize = 12.sp, color = Color.Gray)
                    }
                }
            }
        }

        item {
            Text("Semua Komunitas yang Diikuti (${joinedClubs.size})", fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }
        items(joinedClubs) { club ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onNavigateToClub(club.id) }
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(club.name, fontWeight = FontWeight.Bold)
                        Text("${club.category} • ${studentProjects.count { it.clubID == club.id }} Proyek", fontSize = 12.sp, color = Color.Gray)
                    }
                    Badge(containerColor = Color(0xFFE0F2F1)) {
                        Text("Joined ✓", color = Color(0xFF00796B), modifier = Modifier.padding(4.dp))
                    }
                }
            }
        }
        item {
            Text("Semua Proyek Mahasiswa (${studentProjects.size})", fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }
        items(studentProjects) { project ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(project.clubName, fontSize = 10.sp, color = Color.Gray)
                    Text(project.title, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(project.description, fontSize = 12.sp, color = Color.DarkGray, maxLines = 2)
                }
            }
        }
    }
}