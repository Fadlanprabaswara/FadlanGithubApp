package com.example.fadlangithub.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Insert
    suspend fun addToFavorite( favorite: Favorite)

    @Query("SELECT * FROM favorite_user")
    fun getFavoriteUSer(): LiveData<List<Favorite>>

    @Query("SELECT count(*) FROM favorite_user WHERE favorite_user.id = :id")
    suspend fun checkUser(id: Int): Int

    @Query("DELETE FROM favorite_user WHERE favorite_user.id = :id")
    suspend fun removeFromFavorite(id: Int): Int
}