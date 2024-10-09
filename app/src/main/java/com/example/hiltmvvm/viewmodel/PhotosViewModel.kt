package com.example.hiltmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hiltmvvm.core.model.Photos
import com.example.hiltmvvm.core.model.Resource
import com.example.hiltmvvm.core.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel
@Inject
constructor(private val repository: Repository) : ViewModel() {
    private var photos: MutableLiveData<Resource<ArrayList<Photos>>> = MutableLiveData()
    val photoList: LiveData<Resource<ArrayList<Photos>>>
        get() = photos

    fun getData() = viewModelScope.launch {
        photos.postValue(Resource.Loading())
        photos.postValue(repository.getAllPhotos())
    }

    fun clearList() {
        photos = MutableLiveData()
    }
}