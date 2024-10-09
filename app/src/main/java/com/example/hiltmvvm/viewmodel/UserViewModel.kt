package com.example.hiltmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hiltmvvm.core.constant.StringConstants.Companion.ERROR_MSG
import com.example.hiltmvvm.core.model.Resource
import com.example.hiltmvvm.core.model.Users
import com.example.hiltmvvm.core.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class UserViewModel
@Inject
constructor(private val repository: Repository) : ViewModel() {

    private var users: MutableLiveData<ArrayList<Users>> = MutableLiveData()
    private var searchList: MutableLiveData<Resource<ArrayList<Users>>> = MutableLiveData()
    val userList: LiveData<Resource<ArrayList<Users>>>
        get() = searchList

    fun getData() = viewModelScope.launch {
        searchList.postValue(Resource.Loading())
        val user = repository.getAllUser()
        if (user is Resource.Success) {
            users.value = user.data
        }
        searchList.postValue(user)
    }

    fun searchText(text: String) {
        try {
            val searchText = text.trim().lowercase()
            if (searchText.isEmpty()) {
                users.value?.let {
                    searchList.postValue(Resource.Success(it))
                }
            } else {
                searchList.postValue(Resource.Loading())
                val tempList = users.value!!.filter { user ->
                    user.name.lowercase().contains(searchText) || user.email.lowercase()
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
            searchList.postValue(Resource.Error(ERROR_MSG))
        }

    }

    fun clearList() {
        users = MutableLiveData()
    }

}