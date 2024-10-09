package com.example.hiltmvvm.core.model

import java.io.Serializable

data class Posts(val userId: Int, val id: Int, val title: String, val body: String) : Serializable
