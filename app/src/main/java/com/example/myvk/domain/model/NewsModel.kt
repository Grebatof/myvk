package com.example.myvk.domain.model

data class NewsModel(
    val groupIcon: String,
    val groupName: String,
    val date: String,
    val text: String,
    val photos: List<String>,
    val likes: String,
    val comments: String,
    val reposts: String,
    val views: String,
)