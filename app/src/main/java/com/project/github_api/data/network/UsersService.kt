package com.project.github_api.data.network

import com.project.github_api.R
import com.project.github_api.data.model.Users
import com.project.github_api.data.model.UsersDetail
import com.project.github_api.data.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface UsersService {

    //Endpoint search
    @GET("search/users")
    @Headers("Authorization: token 6b71719569ed6f8a91ac78ccf7819395a40a3672")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<UserResponse>

    //Endpoint detail
    @GET("users/{username}")
    @Headers("Authorization: token 6b71719569ed6f8a91ac78ccf7819395a40a3672")
    fun getDetailUsers(
            @Path("username") username: String
    ): Call<UsersDetail>

    //Endpoint follower
    @GET("users/{username}/followers")
    @Headers("Authorization: token 6b71719569ed6f8a91ac78ccf7819395a40a3672")
    fun getFollowers(
            @Path("username") username: String
    ): Call<ArrayList<Users>>

    //Endpoint following
    @GET("users/{username}/following")
    @Headers("Authorization: token 6b71719569ed6f8a91ac78ccf7819395a40a3672")
    fun getFollowing(
            @Path("username") username: String
    ): Call<ArrayList<Users>>
}