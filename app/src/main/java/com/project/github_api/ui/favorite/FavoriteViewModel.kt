package com.project.github_api.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.project.github_api.data.dao.UsersFavorite
import com.project.github_api.data.dao.UsersFavoriteDAO
import com.project.github_api.data.database.FavoriteDatabase

class FavoriteViewModel(application: Application): AndroidViewModel(application) {
    private var favoritDAO: UsersFavoriteDAO?
    private var favoritedatabase: FavoriteDatabase?

    init {
        favoritedatabase = FavoriteDatabase.getDatabase(application)
        favoritDAO = favoritedatabase?.usersfavoriteDao()
    }

    fun getFavorite(): LiveData<List<UsersFavorite>>? {
        return favoritDAO?.getFavorite()
    }
}