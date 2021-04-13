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

class DetailActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_DETAIL = "extra_detail"
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var modelView: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_DETAIL)
        modelView = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)

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

        val detail = Bundle()
        detail.putString(EXTRA_DETAIL, username)

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