package com.project.github_api.data.dao

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "user_favorite")
data class UsersFavorite(
    val login: String,
    @PrimaryKey
    val id: Int,
    val avatar_url : String,
    val html_url: String
):Serializable
