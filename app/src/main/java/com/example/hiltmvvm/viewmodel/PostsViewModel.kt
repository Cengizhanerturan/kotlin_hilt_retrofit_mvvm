package com.example.hiltmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hiltmvvm.core.constant.StringConstants
import com.example.hiltmvvm.core.model.Posts
import com.example.hiltmvvm.core.model.Resource
import com.example.hiltmvvm.core.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class PostsViewModel
@Inject
constructor(private val repository: Repository) : ViewModel() {
    private var posts: MutableLiveData<ArrayList<Posts>> = MutableLiveData()
    private var searchList: MutableLiveData<Resource<ArrayList<Posts>>> = MutableLiveData()
    val postList: LiveData<Resource<ArrayList<Posts>>>
        get() = searchList

    fun getData() = viewModelScope.launch {
        searchList.postValue(Resource.Loading())
        val post = repository.getAllPost()
        if (post is Resource.Success) {
            posts.value = post.data
        }
        searchList.postValue(post)
    }

    fun searchText(text: String) {
        try {
            val searchText = text.trim().lowercase()
            if (searchText.isEmpty()) {
                posts.value?.let {
                    searchList.postValue(Resource.Success(it))
                }
            } else {
                searchList.postValue(Resource.Loading())
                val tempList = posts.value!!.filter { post ->
                    post.title.lowercase().contains(searchText) || post.body.lowercase()
                        .contains(searchText)
                }
                val filteredList = ArrayList(tempList)

                if (filteredList.isEmpty()) {
                    searchList.postValue(Resource.Empty())
                } else {
                    searchList.postValue(Resource.Success(filteredList))
                }
            }
        } catch (e: Exception) {
            searchList.postValue(Resource.Error(StringConstants.ERROR_MSG))
        }

    }

    fun clearList() {
        posts = MutableLiveData()
    }
}