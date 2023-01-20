package com.bitflyer.testapp.ui.userlist.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bitflyer.testapp.databinding.LayoutUserListItemBinding
import com.bitflyer.testapp.ui.userlist.model.UserBriefModel

class UserViewHolder(private val binding: LayoutUserListItemBinding, private val onUserClickListener: OnUserClickListener) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(model: UserBriefModel) {
        binding.userName.text = model.login
        binding.root.setOnClickListener { onUserClickListener.onUserClick(model) }
    }
}