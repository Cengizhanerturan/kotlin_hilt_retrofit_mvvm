package com.example.hiltmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hiltmvvm.core.model.Posts
import com.example.hiltmvvm.core.model.Resource
import com.example.hiltmvvm.core.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel
@Inject
constructor(private val repository: Repository) : ViewModel() {

    private var posts: MutableLiveData<Resource<ArrayList<Posts>>> = MutableLiveData()
    val postList: LiveData<Resource<ArrayList<Posts>>>
        get() = posts

    fun getData(userID: Int) = viewModelScope.launch {
        posts.postValue(Resource.Loading())
        posts.postValue(repository.getPostByUser(userID))
    }

    fun clearList() {
        posts = MutableLiveData()
    }
}