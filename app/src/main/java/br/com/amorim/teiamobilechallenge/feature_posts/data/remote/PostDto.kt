package br.com.amorim.teiamobilechallenge.feature_posts.data.remote

data class PostDto(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String
)
