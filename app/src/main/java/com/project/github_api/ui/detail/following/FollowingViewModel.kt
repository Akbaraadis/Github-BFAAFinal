package com.project.github_api.ui.detail.following

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.github_api.data.model.Users
import com.project.github_api.data.network.UsersClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel: ViewModel() {
    val listFollowing = MutableLiveData<ArrayList<Users>>()

    fun setListFollowing(username: String){
        UsersClient.apiInstance
                .getFollowing(username)
                .enqueue(object : Callback<ArrayList<Users>> {
                    override fun onResponse(call: Call<ArrayList<Users>>, response: Response<ArrayList<Users>>) {
                        if (response.isSuccessful){
                            listFollowing.postValue(response.body())
                        }
                    }

                    override fun onFailure(call: Call<ArrayList<Users>>, t: Throwable) {
                        Log.d("Failure", t.message.toString())
                    }

                })
    }

    fun getListFollowing(): LiveData<ArrayList<Users>> {
        return listFollowing
    }
}