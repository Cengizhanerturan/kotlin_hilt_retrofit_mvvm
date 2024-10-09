package com.example.hiltmvvm.core.repository

import com.example.hiltmvvm.core.constant.StringConstants.Companion.ERROR_MSG
import com.example.hiltmvvm.core.model.Comments
import com.example.hiltmvvm.core.model.Photos
import com.example.hiltmvvm.core.model.Posts
import com.example.hiltmvvm.core.model.Resource
import com.example.hiltmvvm.core.model.Users
import com.example.hiltmvvm.core.service.RetrofitApi
import java.lang.Exception
import javax.inject.Inject

class Repository
@Inject
constructor(private val api: RetrofitApi) {

    suspend fun getAllPost(): Resource<ArrayList<Posts>> {
        try {
            val response = api.getAllPost()
            if (response.isSuccessful) {
                response.body()?.let { postList ->
                    return Resource.Success(postList)
                }
            }
            return Resource.Error(ERROR_MSG)
        } catch (e: Exception) {
            return Resource.Error(ERROR_MSG)
        }
    }

    suspend fun getAllUser(): Resource<ArrayList<Users>> {
        try {
            val response = api.getAllUser()
            if (response.isSuccessful) {
                response.body()?.let { userList ->
                    return Resource.Success(userList)
                }
            }
            return Resource.Error(ERROR_MSG)
        } catch (e: Exception) {
            return Resource.Error(ERROR_MSG)
        }
    }

    suspend fun getPostByUser(userID: Int): Resource<ArrayList<Posts>> {
        try {
            val response = api.getPostByUser(userID)
            if (response.isSuccessful) {
                response.body()?.let { postList ->
                    return Resource.Success(postList)
                }
            }
            return Resource.Error(ERROR_MSG)
        } catch (e: Exception) {
            return Resource.Error(ERROR_MSG)
        }
    }

    suspend fun getAllPhotos(): Resource<ArrayList<Photos>> {
        try {
            val response = api.getAllPhotos()
            if (response.isSuccessful) {
                response.body()?.let { photoList ->
                    return Resource.Success(photoList)
                }
            }
            return Resource.Error(ERROR_MSG)
        } catch (e: Exception) {
            return Resource.Error(ERROR_MSG)
        }
    }

    suspend fun getCommentsByPost(postID: Int): Resource<ArrayList<Comments>> {
        try {
            val response = api.getCommentsByPost(postID)
            if (response.isSuccessful) {
                response.body()?.let { commentList ->
                    return Resource.Success(commentList)
                }
            }
            return Resource.Error(ERROR_MSG)
        } catch (e: Exception) {
            return Resource.Error(ERROR_MSG)
        }
    }
}