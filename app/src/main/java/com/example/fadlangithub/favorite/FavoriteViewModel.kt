package com.example.fadlangithub.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.fadlangithub.data.Favorite
import com.example.fadlangithub.data.FavoriteDao
import com.example.fadlangithub.data.FavoriteDatabase

class FavoriteViewModel(application: Application): AndroidViewModel(application) {

    private var userDao: FavoriteDao?
    private var userDb: FavoriteDatabase?

    init {
        userDb = FavoriteDatabase.getDatabase(application)
        userDao = userDb?.favoriteUSerDao()
    }

    fun getFavoriteUser() : LiveData<List<Favorite>>? {
        return userDao?.getFavoriteUSer()
    }

}