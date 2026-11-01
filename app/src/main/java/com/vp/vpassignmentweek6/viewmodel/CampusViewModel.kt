package com.vp.vpassignmentweek6.viewmodel

import android.adservices.adid.AdId
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.vp.vpassignmentweek6.model.Community
import com.vp.vpassignmentweek6.model.Project

class CampusViewModel: ViewModel() {
    var communities by mutableStateOf(
        listOf(
            Community(
                "gdsc",
                "Google Developer Student Club",
                "Android, Flutter, Firebase",
                "Fokus pada ekosistem Google: Android Compose, Flutter, Firebase, dan Google Cloud Platform.",
                142,
                true
            ),
            Community(
                "cyber",
                "CyberSecurity Guild",
                "Security & Pentesting",
                "Eksplorasi ethical hacking, CTF challenges, reverse engineering, dan pertahanan jaringan kampus.",
                86,
                true
            ),
            Community(
                "mobile",
                "Mobile Dev Community",
                "Jetpack Compose & KMP",
                "Native Jetpack Compose & Kotlin Multiplatform development.",
                95,
                true
            ),
            Community(
                "ai",
                "AI Society",
                "Machine Learning & LLM",
                "Riset terapan deep learning, computer vision, agen cerdas, dan fine-tuning large language models.",
                115,
                false
            ),
            Community(
                "web",
                "Web Wizards",
                "React & Laravel",
                "Full-stack modern web development menggunakan React, Next.js, dan Laravel.",
                120,
                false
            ),
            Community(
                "cloud",
                "Cloud & DevOps",
                "Docker & Kubernetes",
                "Otomasi deployment, containerization, dan cloud infrastructure.",
                78,
                false
            )
        )
    )
        private set

    var projects by mutableStateOf(
        listOf(
            Project(
                "p-gdsc-01",
                "gdsc",
                "Google Developer Student Club",
                "Smart Campus Navigation",
                "Sistem navigasi peta gedung kampus interaktif berbasis Jetpack Compose dan Google Maps SDK. Memfasilitasi mahasiswa dan dosen menemukan ruang perkuliahan, laboratorium, perpustakaan, serta fasilitas umum kampus secara akurat.",
                listOf("Kotlin", "Jetpack Compose", "Room", "Maps SDK"),
                "In Progress"
            ),
            Project(
                "p-gdsc-02",
                "gdsc",
                "Google Developer Student Club",
                "Student Attendance QR Scanner",
                "Reminder barcode & QR presensi kehadiran workshop/kuliah. Integrasi CameraX & Firebase untuk sinkronisasi data absensi secara real-time.",
                listOf("Jetpack Compose", "Firebase", "CameraX"),
                "Completed"
            ),
            Project(
                "p-gdsc-03",
                "gdsc",
                "Google Developer Student Club",
                "Developer RSVP Portal",
                "Portal pendaftaran webinar dan bootcamp dengan manajemen kursi otomatis dan sinkronisasi Ktor API server.",
                listOf("Kotlin", "Ktor", "Compose"),
                "In Progress"
            ),
            Project(
                "p-cyber-01",
                "cyber",
                "CyberSecurity Guild",
                "Campus Firewall Log Analyzer",
                "Dashboard analitik log jaringan untuk mendeteksi potensi serangan brute-force secara otomatis.",
                listOf("Python", "Room"),
                "Completed"
            )
        )
    )
        private set

    val joinedClubs: Int
        get() = communities.count { it.isJoined }
    val totalProjects: Int
        get() = projects.size

    fun toggleJoin(communityId: String) {
        communities = communities.map {
            if (it.id == communityId) it.copy(isJoined = !it.isJoined) else it
        }
    }
    fun addProject(clubId: String, title: String, description: String, techStack: List<String>) {
        val club = communities.find { it.id == clubId } ?: return
        val newProject = Project(
            id = "p-${clubId}-${System.currentTimeMillis()}",
            clubID = clubId,
            clubName = club.name,
            title = title,
            description = description,
            techStack = techStack,
            status = "In Progress"
        )
        projects = projects + newProject
    }

    fun deleteProject(projectId: String) {
        projects = projects.filter { it.id != projectId }
    }

    fun updateProjectStatus(projectId: String, newStatus: String) {
        projects = projects.map {
            if (it.id == projectId) it.copy(status = newStatus) else it
        }
    }
}