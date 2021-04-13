package com.project.github_api.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.project.github_api.R
import com.project.github_api.databinding.ActivityDetailBinding
import com.project.github_api.ui.detail.viewpager.ViewPagerAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_DETAIL = "extra_detail"
        const val EXTRA_FAVORITE = "extra_favorite"
        const val EXTRA_AVATAR = "extra_avatar"
        const val EXTRA_URL = "extra_url"
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var modelView: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_DETAIL)
        val idfavorite = intent.getIntExtra(EXTRA_FAVORITE, 0)
        val avatar_url = intent.getStringExtra(EXTRA_AVATAR)
        val html_url = intent.getStringExtra(EXTRA_URL)

        modelView = ViewModelProvider(this).get(DetailViewModel::class.java)

        modelView.setUserDetail(username.toString())
        modelView.getUserDetail().observe(this, {
            if(it != null){
                showLoading(false)
                binding.apply {
                    if(it.name!=null){
                        tvDetailName.text = it.name
                    }
                    else{
                        tvDetailName.text = it.login
                    }
                    supportActionBar?.title = it.login
                    tvDetailCompany.text = it.company
                    tvDetailLocation.text = it.location
                    tvDetailFollowers.text = it.followers.toString()
                    tvDetailFollowing.text = it.following.toString()
                    tvDetailRepository.text = it.public_repos.toString()
                    Glide.with(this@DetailActivity)
                        .load(it.avatar_url)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .into(ivDetailAvatar)
                }
            }
            else
                showLoading(true)
        })

        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = modelView.checkFavorite(idfavorite)
            withContext(Dispatchers.Main){
                if(count != null){
                    if(count > 0){
                        binding.detailFavorite.isChecked = true
                        _isChecked = true
                    }
                    else{
                        binding.detailFavorite.isChecked = false
                        _isChecked = false
                    }
                }
            }
        }

        val detail = Bundle()
        detail.putString(EXTRA_DETAIL, username)


        binding.detailFavorite.setOnClickListener{
            _isChecked = !_isChecked
            if(_isChecked){
                modelView.addFavorite(username.toString(), idfavorite, avatar_url.toString(), html_url.toString())
            }
            else{
                modelView.removeFavorite(idfavorite)
            }
            binding.detailFavorite.isChecked = _isChecked
        }

        val viewPager = ViewPagerAdapter(this, supportFragmentManager, detail)
        binding.apply {
            detailViewpager.adapter = viewPager
            detailTab.setupWithViewPager(detailViewpager)
        }

    }

    private fun showLoading(state: Boolean){
        if(state){
            binding.detailConstraintCard.visibility = View.GONE
            binding.detailConstraintCardview.visibility = View.GONE
            binding.detailProgress.visibility = View.VISIBLE
        }
        else{
            binding.detailProgress.visibility = View.GONE
            binding.detailConstraintCard.visibility = View.VISIBLE
            binding.detailConstraintCardview.visibility = View.VISIBLE
        }
    }
}