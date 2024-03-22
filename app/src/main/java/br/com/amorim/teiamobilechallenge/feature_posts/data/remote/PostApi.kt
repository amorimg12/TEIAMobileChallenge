package br.com.amorim.teiamobilechallenge.feature_posts.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface PostApi {
    @GET("posts")
    suspend fun getPosts(
        @Query("page") page: Int,
        @Query("per_page") pageCount: Int
    ): List<PostDto>

    companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    }
}