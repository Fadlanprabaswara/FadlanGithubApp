package com.example.fadlangithub.data


import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favorite_user")
data class Favorite(
    val login: String,
    @PrimaryKey
    val id: Int,
    val avatarUrl : String
) : Serializable
