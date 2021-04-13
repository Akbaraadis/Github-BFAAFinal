package com.project.github_api.ui.main

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.project.github_api.data.model.Users
import com.project.github_api.databinding.ItemRowUsersBinding

class MainAdapter : RecyclerView.Adapter<MainAdapter.userViewHolder>() {

    private val list = ArrayList<Users>()

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    fun setList(users: ArrayList<Users>){
        list.clear()
        list.addAll(users)
        notifyDataSetChanged()
        Log.d("Message", users.toString())
    }

    inner class userViewHolder(val binding: ItemRowUsersBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(user: Users){
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(user)
            }

            binding.apply {
                Glide.with(itemView)
                    .load(user.avatar_url)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(imgItemPhoto)

                tvItemName.text = user.login
                tvItemCompany.text = user.html_url
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): userViewHolder {
        val view = ItemRowUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return userViewHolder((view))
    }

    override fun onBindViewHolder(holder: userViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback{
        fun onItemClicked(data: Users)
    }
}