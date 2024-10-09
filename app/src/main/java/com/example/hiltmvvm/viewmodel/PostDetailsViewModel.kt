package com.example.hiltmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hiltmvvm.core.model.Comments
import com.example.hiltmvvm.core.model.Resource
import com.example.hiltmvvm.core.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailsViewModel
@Inject
constructor(private val repository: Repository) : ViewModel() {
    private var comments: MutableLiveData<Resource<ArrayList<Comments>>> = MutableLiveData()
    val commentList: LiveData<Resource<ArrayList<Comments>>>
        get() = comments

    fun getData(postID: Int) = viewModelScope.launch {
        comments.postValue(Resource.Loading())
        comments.postValue(repository.getCommentsByPost(postID))
    }

    fun clearList() {
        comments = MutableLiveData()
    }
}