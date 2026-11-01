package com.vp.vpassignmentweek6.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vp.vpassignmentweek6.viewmodel.CampusViewModel
import kotlinx.coroutines.launch

@Composable
fun Communities(viewModel: CampusViewModel, onNavigateToClub: (String) -> Unit) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text("Communities Directory", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text("Pilih komunitas untuk membuka Club Projects", fontSize = 12.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(8.dp))
            }

            items(viewModel.communities) { community ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        if (community.isJoined) {
                            onNavigateToClub(community.id)
                        } else {
                            scope.launch {
                                snackbarHostState.showSnackbar("Harap bergabung ke komunitas terlebih dahulu!")
                            }
                        }
                    }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(community.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                Text("${community.category} • ${community.memberCount} Mahasiswa", fontSize = 12.sp, color = Color.Gray)
                            }
                            Button(
                                onClick = { viewModel.toggleJoin(community.id) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (community.isJoined) Color(0xFFD32F2F) else Color(0xFF1E3C72)
                                )
                            ) {
                                Text(if (community.isJoined) "Joined" else "+ Join")
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(community.description, fontSize = 12.sp, color = Color.DarkGray, maxLines = 2)
                    }
                }
            }
        }
    }
}