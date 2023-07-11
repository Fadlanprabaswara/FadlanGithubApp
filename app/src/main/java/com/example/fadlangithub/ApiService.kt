package com.example.fadlangithub

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getResponUser(
        @Query("q") query: String
    ): Call<ResponseUser>

    @GET("users")
    fun getListUser(): Call<List<User>>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<ResponseDetail>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<User>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<User>>
}