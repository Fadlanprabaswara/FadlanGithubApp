package com.example.fadlangithub.main

import android.util.Log
import androidx.lifecycle.*
import com.example.fadlangithub.ApiConfig
import com.example.fadlangithub.ResponseUser
import com.example.fadlangithub.User
import com.example.fadlangithub.setting.SettingPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val preferences: SettingPreferences) : ViewModel() {

    private val _listUser = MutableLiveData<List<User>?>()
    val listUser: MutableLiveData<List<User>?> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getTheme() = preferences.getThemeSetting().asLiveData()

    class Factory(private val preferences: SettingPreferences) :
        ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            MainViewModel(preferences) as T
    }

    companion object{
        private const val TAG = "MainViewModel"
    }

    init {
        findResponseUser()
    }

    private fun findResponseUser(){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListUser()
        client.enqueue(object : retrofit2.Callback<List<User>> {
            override fun onResponse(
                call: Call<List<User>>,
                response: Response<List<User>>
            ) {
                _isLoading.value=false
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null){
                        _listUser.value = responseBody
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun searchUser(user : String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getResponUser(user)
        client.enqueue(object : Callback<ResponseUser> {
            override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>) {
                _isLoading.value=false
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null){
                        _listUser.value = responseBody.items
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

}