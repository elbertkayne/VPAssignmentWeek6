package com.vp.vpassignmentweek6.model

data class Community(
    val id: String,
    val name: String,
    val category: String,
    val description: String,
    val memberCount: Int,
    val isJoined: Boolean
)

data class Project(
    val id: String,
    val clubID: String,
    val clubName: String,
    val title: String,
    val description: String,
    val techStack: List <String>,
    val status: String
)