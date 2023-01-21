package com.bitflyer.testapp.ui.userlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.bitflyer.testapp.R
import com.bitflyer.testapp.databinding.LayoutUserItemLoadingBinding

class UserListLoadingAdapter(private val onRetryClickListener: OnRetryClickListener): LoadStateAdapter<UserLoadingViewHolder>() {

    override fun onBindViewHolder(holder: UserLoadingViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): UserLoadingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_user_item_loading, parent, false)
        val binding = LayoutUserItemLoadingBinding.bind(view)
        return UserLoadingViewHolder(binding, onRetryClickListener)
    }
}

interface OnRetryClickListener {
    fun retryLoading()
}