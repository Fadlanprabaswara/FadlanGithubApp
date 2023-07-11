package com.example.fadlangithub.detailuser

import android.app.Application
import android.app.DownloadManager.Query
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fadlangithub.ApiConfig
import com.example.fadlangithub.ResponseDetail
import com.example.fadlangithub.data.Favorite
import com.example.fadlangithub.data.FavoriteDao
import com.example.fadlangithub.data.FavoriteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {


    private var _detailUser = MutableLiveData<ResponseDetail?>()
    val detailuser: MutableLiveData<ResponseDetail?> = _detailUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    private var userDao: FavoriteDao?
    private var userDb: FavoriteDatabase?

    init {
        userDb = FavoriteDatabase.getDatabase(application)
        userDao = userDb?.favoriteUSerDao()
    }

    companion object {
        private const val TAG = "DetailViewModel"
    }


    fun detailResponseUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<ResponseDetail> {
            override fun onResponse(
                call: Call<ResponseDetail>,
                response: Response<ResponseDetail>,
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _detailUser.value = responseBody
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseDetail>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }


    fun addToFavorite(query: String, id: Int, avatarUrl:String) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = Favorite(query, id, avatarUrl)
            userDao?.addToFavorite(user)
        }
    }

    suspend fun checkUser(id: Int) = userDao?.checkUser(id)

    fun removeFromFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFromFavorite(id)
        }
    }

}