package com.project.github_api.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.github_api.data.network.UsersClient
import com.project.github_api.data.response.UserResponse
import com.project.github_api.data.model.Users
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {

    val listUsers = MutableLiveData<ArrayList<Users>>()

    fun setSearchUsers(query: String){
        UsersClient.apiInstance
            .getSearchUsers(query)
            .enqueue(object : Callback<UserResponse>{
                override fun onResponse(
                        call: Call<UserResponse>,
                        response: Response<UserResponse>
                ) {
                    if(response.isSuccessful){
                        listUsers.postValue(response.body()?.items)
                        Log.d("Response Code", response.code().toString())
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })
    }

    fun getSearchUsers(): LiveData<ArrayList<Users>>{
        return listUsers
    }
}