package com.bitflyer.testapp.ui.userlist.adapter

import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.bitflyer.testapp.databinding.LayoutUserItemLoadingBinding
import com.bitflyer.testapp.util.setOnClickListener

class UserLoadingViewHolder(
    private val binding: LayoutUserItemLoadingBinding,
    private val retryClickListener: OnRetryClickListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(loadState: LoadState) {
        binding.shimmer.root.isVisible = loadState is LoadState.Loading
        binding.error.root.isVisible = loadState is LoadState.Error
        binding.error.tryAgain.setOnClickListener(500) { retryClickListener.retryLoading() }
    }
}