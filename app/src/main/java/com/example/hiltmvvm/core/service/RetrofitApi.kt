package com.example.hiltmvvm.core.service

import com.example.hiltmvvm.core.model.Comments
import com.example.hiltmvvm.core.model.Photos
import com.example.hiltmvvm.core.model.Posts
import com.example.hiltmvvm.core.model.Users
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi {
    @GET("posts")
    suspend fun getAllPost(): Response<ArrayList<Posts>>

    @GET("posts")

    suspend fun getPostByUser(@Query("userId") userID: Int): Response<ArrayList<Posts>>

    @GET("users")
    suspend fun getAllUser(): Response<ArrayList<Users>>

    @GET("photos")
    suspend fun getAllPhotos(): Response<ArrayList<Photos>>

    @GET("comments")
    suspend fun getCommentsByPost(@Query("postId") postID: Int): Response<ArrayList<Comments>>
}