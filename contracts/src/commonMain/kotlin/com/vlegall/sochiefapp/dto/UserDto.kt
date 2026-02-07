package com.vlegall.sochiefapp.dto

data class UserDto(
    val id: String,
    val name: String,
    val email: String? = null
)