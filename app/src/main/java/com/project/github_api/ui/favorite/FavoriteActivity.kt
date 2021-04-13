package com.project.github_api.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.github_api.R
import com.project.github_api.data.dao.UsersFavorite
import com.project.github_api.data.model.Users
import com.project.github_api.databinding.ActivityFavoriteBinding
import com.project.github_api.ui.detail.DetailActivity
import com.project.github_api.ui.main.MainAdapter

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFavoriteBinding
    private lateinit var adapter: FavoriteAdapter
    private lateinit var modelView: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = FavoriteAdapter()
        adapter.notifyDataSetChanged()

        modelView = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Users) {
                val intent = Intent(this@FavoriteActivity, DetailActivity::class.java)
                intent.apply {
                    putExtra(DetailActivity.EXTRA_DETAIL, data.login)
                    putExtra(DetailActivity.EXTRA_FAVORITE, data.id)
                    putExtra(DetailActivity.EXTRA_AVATAR, data.avatar_url)
                    putExtra(DetailActivity.EXTRA_URL, data.html_url)
                }
                startActivity(intent)
            }
        })

        modelView.getFavorite()?.observe(this, {
            if(it != null){
                val list = convertList(it)
                adapter.setList(list)
            }
        })

        binding.apply {
            favoriteRv.setHasFixedSize(true)
            favoriteRv.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            favoriteRv.adapter = adapter
        }


    }

    private fun convertList(users: List<UsersFavorite>): ArrayList<Users> {
        val listUser = ArrayList<Users>()
        for(user in users){
            val mappedUser = Users(
                user.login,
                user.id,
                user.avatar_url,
                user.html_url
            )
            listUser.add(mappedUser)
        }
        return listUser
    }

    override fun onBackPressed() {
        finishAffinity()
//        super.onBackPressed()
//        val intent = Intent(Intent.ACTION_MAIN)
//        intent.addCategory(Intent.CATEGORY_HOME)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//        startActivity(intent)
    }
}