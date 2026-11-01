package com.vp.vpassignmentweek6.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.vp.vpassignmentweek6.viewmodel.CampusViewModel

@Composable
fun Campus(viewModel: CampusViewModel = viewModel()) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val showBottomBar = currentRoute == "dashboard" || currentRoute == "communities"

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    NavigationBarItem(
                        selected = currentRoute == "dashboard",
                        onClick = { navController.navigate("dashboard") { launchSingleTop = true; restoreState = true } },
                        icon = { Icon(Icons.Default.Home, contentDescription = "Dashboard") },
                        label = { Text("Dashboard") }
                    )
                    NavigationBarItem(
                        selected = currentRoute == "communities",
                        onClick = { navController.navigate("communities") { launchSingleTop = true; restoreState = true } },
                        icon = { Icon(Icons.Default.List, contentDescription = "Communities") },
                        label = { Text("Communities") }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "dashboard",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("dashboard") {
                Dashboard(
                    viewModel = viewModel,
                    onNavigateToClub = { clubId -> navController.navigate("club_projects/$clubId") }
                )
            }
            composable("communities") {
                Communities(
                    viewModel = viewModel,
                    onNavigateToClub = { clubId -> navController.navigate("club_projects/$clubId") }
                )
            }
           composable(
                route = "club_projects/{clubId}",
                arguments = listOf(navArgument("clubId") { type = NavType.StringType })
            ) { backStackEntry ->
                val clubId = backStackEntry.arguments?.getString("clubId") ?: ""
                ClubProjects(
                    viewModel = viewModel,
                    clubId = clubId,
                    onBackClick = { navController.popBackStack() },
                    onNavigateToProjectDetail = { projectId -> navController.navigate("project_detail/$projectId") }
                )
            }
            composable(
                route = "project_detail/{projectId}",
                arguments = listOf(navArgument("projectId") { type = NavType.StringType })
            ) { backStackEntry ->
                val projectId = backStackEntry.arguments?.getString("projectId") ?: ""
                ProjectDetail(
                    viewModel = viewModel,
                    projectId = projectId,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}