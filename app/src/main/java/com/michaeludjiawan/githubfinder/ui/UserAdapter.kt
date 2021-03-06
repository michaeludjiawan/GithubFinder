package com.michaeludjiawan.githubfinder.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.michaeludjiawan.githubfinder.R
import com.michaeludjiawan.githubfinder.data.model.User
import kotlinx.android.synthetic.main.item_user.view.*

class UserAdapter : PagingDataAdapter<User, RecyclerView.ViewHolder>(USER_COMPARATOR) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val user = getItem(position)
        if (user != null) {
            (holder as UserViewHolder).bind(user)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder = UserViewHolder.create(parent)

    companion object {
        val USER_COMPARATOR = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean = oldItem == newItem
        }
    }
}

class UserViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    fun bind(user: User) {
        with(view) {
            Glide.with(view.context)
                .load(user.avatarUrl)
                .apply(RequestOptions().placeholder(android.R.color.darker_gray))
                .into(iv_user_avatar)

            tv_user_name.text = user.loginName
        }
    }

    companion object {
        fun create(
            parent: ViewGroup
        ): UserViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
            return UserViewHolder(view)
        }
    }
}