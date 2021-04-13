package com.project.github_api.ui.detail.follower

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.github_api.R
import com.project.github_api.databinding.FragmentFollowBinding
import com.project.github_api.ui.detail.DetailActivity
import com.project.github_api.ui.main.MainAdapter

class FollowerFragment: Fragment(R.layout.fragment_follow) {

    private var _binding : FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private lateinit var username: String
    private lateinit var viewModel: FollowerViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val argument = arguments
        username = argument?.getString(DetailActivity.EXTRA_DETAIL).toString()

        _binding = FragmentFollowBinding.bind(view)

        var adapter = MainAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            followRvList.setHasFixedSize(true)
            followRvList.layoutManager = LinearLayoutManager(activity)
            followRvList.adapter = adapter
        }

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowerViewModel::class.java)
        viewModel.setListFollower(username)
        viewModel.getListFollower().observe(viewLifecycleOwner, {
            if (it!=null){
                adapter.setList(it)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}