package com.domain.model

data class User(
    val id: Int,
    val name: String,
    val description: String,
    val coverImgUrl: String,
    val status: String,
    val deliveryFee: Int
)
