package com.bitflyer.testapp.ui.userlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bitflyer.testapp.R
import com.bitflyer.testapp.databinding.LayoutUserListItemBinding
import com.bitflyer.testapp.domain.userlist.entity.UserBriefEntity
import com.bitflyer.testapp.ui.BaseMapper
import com.bitflyer.testapp.ui.userlist.model.UserBriefModel

class UserListAdapter(
    private val onUserClickListener: OnUserClickListener,
    private val mapper: BaseMapper<UserBriefEntity, UserBriefModel>
) :
    PagingDataAdapter<UserBriefEntity, UserViewHolder>(RvDiffUtilItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_user_list_item, parent, false)
        val binding = LayoutUserListItemBinding.bind(view)
        return UserViewHolder(binding, onUserClickListener)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(mapper.map(it))
        }
    }
}

private class RvDiffUtilItemCallback : DiffUtil.ItemCallback<UserBriefEntity>() {
    override fun areItemsTheSame(oldItem: UserBriefEntity, newItem: UserBriefEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UserBriefEntity, newItem: UserBriefEntity): Boolean {
        return oldItem.login == newItem.avatarUrl && oldItem.avatarUrl == newItem.avatarUrl
    }
}

interface OnUserClickListener {
    fun onUserClick(user: UserBriefModel)
}