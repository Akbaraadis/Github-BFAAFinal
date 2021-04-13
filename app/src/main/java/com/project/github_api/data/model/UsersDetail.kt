package com.project.github_api.data.model

data class UsersDetail(
        val login: String,
        val id: Int,
        val avatar_url: String,
        val followers_url: String,
        val following_url: String,
        val name: String,
        val company: String,
        val location: String,
        val public_repos: Int,
        val followers: Int,
        val following: Int
)
