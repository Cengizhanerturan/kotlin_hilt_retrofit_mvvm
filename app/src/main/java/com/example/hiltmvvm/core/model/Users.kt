package com.example.hiltmvvm.core.model

import java.io.Serializable

data class Users(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val phone: String,
    val website: String
) : Serializable
