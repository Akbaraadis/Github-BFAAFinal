package com.project.github_api.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UsersFavoriteDAO {
    @Insert
    suspend fun addFavorite(usersFavorite: UsersFavorite)

    @Query("SELECT * FROM user_favorite")
    fun getFavorite(): LiveData<List<UsersFavorite>>

    @Query("SELECT count(*) FROM user_favorite WHERE user_favorite.id = :id")
    suspend fun checkFavorite(id: Int): Int

    @Query("DELETE FROM user_favorite WHERE user_favorite.id = :id")
    suspend fun removeFavorite(id: Int): Int
}