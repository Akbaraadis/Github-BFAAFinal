package com.project.github_api.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.github_api.data.model.UsersDetail
import com.project.github_api.data.network.UsersClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {
    val user = MutableLiveData<UsersDetail>()

    fun setUserDetail(username: String){
        with(UsersClient) {
            apiInstance
                .getDetailUsers(username)
                .enqueue(object : Callback<UsersDetail>{
                    override fun onResponse(call: Call<UsersDetail>, response: Response<UsersDetail>) {
                        if (response.isSuccessful){
                            user.postValue(response.body())
                        }
                    }

                    override fun onFailure(call: Call<UsersDetail>, t: Throwable) {
                        Log.d("Failure", t.message.toString())
                    }

                })
        }
    }

    fun getUserDetail(): LiveData<UsersDetail>{
        return user
    }
}