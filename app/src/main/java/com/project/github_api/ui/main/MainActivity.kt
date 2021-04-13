package com.project.github_api.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.github_api.R
import com.project.github_api.data.model.Users
import com.project.github_api.databinding.ActivityMainBinding
import com.project.github_api.ui.detail.DetailActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MainAdapter

    private val userItems: MutableList<Users> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = "Github User"
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = MainAdapter()
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallback(object : MainAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Users) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_DETAIL, data.login)
                intent.putExtra(DetailActivity.EXTRA_FAVORITE, data.id)
                startActivity(intent)
            }
        })
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        binding.apply {
            mainRvUser.layoutManager = LinearLayoutManager(this@MainActivity)
            mainRvUser.setHasFixedSize(true)
            mainRvUser.adapter = adapter

            mainSvSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                    androidx.appcompat.widget.SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let {
                        showLoading(true)
                        if (it.isNotEmpty()) {
                            userItems.clear()
                            viewModel.setSearchUsers(query)
                            mainSvSearch.clearFocus()
                        } else {
                            mainSvSearch.clearFocus()
                        }
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean = false
            })

        }

        viewModel.getSearchUsers().observe(this, {
            if (it!=null){
                adapter.setList(it)
                showLoading(false)
            }
            else{
                showNotFound(true)
            }
        })

    }

    private fun showLoading(state: Boolean){
        if(state){
            binding.mainProgress.visibility = View.VISIBLE
        }
        else{
            binding.mainProgress.visibility = View.GONE
        }
    }

    private fun showNotFound(state: Boolean){
        if(state){
            binding.apply {
                mainIvNotfound.visibility = View.VISIBLE
                mainTvNotfound.visibility = View.VISIBLE
            }
        }
        else{
            binding.apply {
                mainIvNotfound.visibility = View.GONE
                mainTvNotfound.visibility = View.GONE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_language, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.change_language) startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        return super.onOptionsItemSelected(item)
    }
}