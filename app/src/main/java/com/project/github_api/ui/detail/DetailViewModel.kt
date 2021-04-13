package com.project.github_api.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.github_api.data.dao.UsersFavorite
import com.project.github_api.data.dao.UsersFavoriteDAO
import com.project.github_api.data.database.FavoriteDatabase
import com.project.github_api.data.model.UsersDetail
import com.project.github_api.data.network.UsersClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application): AndroidViewModel(application) {
    val user = MutableLiveData<UsersDetail>()
    private var favoritDAO: UsersFavoriteDAO?
    private var favoritedatabase: FavoriteDatabase?

    init {
        favoritedatabase = FavoriteDatabase.getDatabase(application)
        favoritDAO = favoritedatabase?.usersfavoriteDao()
    }

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

    fun addFavorite(username: String, id: Int, avatar_url: String, html_url: String){
        CoroutineScope(Dispatchers.IO).launch {
            var favorite = UsersFavorite(
                username,
                id,
                avatar_url,
                html_url
            )
            favoritDAO?.addFavorite(favorite)
        }
    }

    suspend fun checkFavorite(id: Int) = favoritDAO?.checkFavorite(id)

    fun removeFavorite(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            favoritDAO?.removeFavorite(id)
        }
    }

    fun getUserDetail(): LiveData<UsersDetail>{
        return user
    }
}