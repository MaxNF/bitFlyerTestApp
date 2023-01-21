package com.bitflyer.testapp.ui.userlist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.bitflyer.testapp.R
import com.bitflyer.testapp.databinding.FragmentUserListBinding
import com.bitflyer.testapp.domain.userlist.entity.UserBriefEntity
import com.bitflyer.testapp.ui.BaseMapper
import com.bitflyer.testapp.ui.userlist.adapter.OnRetryClickListener
import com.bitflyer.testapp.ui.userlist.adapter.OnUserClickListener
import com.bitflyer.testapp.ui.userlist.adapter.UserListAdapter
import com.bitflyer.testapp.ui.userlist.adapter.UserListLoadingAdapter
import com.bitflyer.testapp.ui.userlist.model.UserBriefModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class UserListFragment : Fragment(), OnUserClickListener, OnRetryClickListener {

    companion object {
        private const val TAG = "UserListFragment"
    }

    private val viewModel: UserListViewModel by viewModels()
    private var binding: FragmentUserListBinding? = null

    lateinit var userListAdapter: UserListAdapter

    @Inject
    lateinit var mapper: BaseMapper<UserBriefEntity, UserBriefModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserListBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.recyclerView?.let {
            binding?.recyclerView?.setOnApplyWindowInsetsListener { view, windowInsets ->
                val bottomInset = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R){
                    windowInsets.getInsets(WindowInsets.Type.systemBars()).bottom
                } else{
                    windowInsets.stableInsetBottom
                }
                view.updatePadding(bottom = bottomInset)
                windowInsets
            }
            userListAdapter = UserListAdapter(this, mapper)
            it.adapter = userListAdapter.withLoadStateFooter(UserListLoadingAdapter(this))
            it.layoutManager = LinearLayoutManager(context)
            val decoration = DividerItemDecoration(requireContext())
            it.addItemDecoration(decoration)
        }
        binding?.listError?.tryAgain?.setOnClickListener { userListAdapter.refresh() }
        observeViewModel()
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.flow.collectLatest {
                userListAdapter.submitData(it)
                Log.d(TAG, "observeViewModel: $it")
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            userListAdapter.loadStateFlow.collect {
                binding?.listError?.root?.isVisible = it.refresh is LoadState.Error
                binding?.listLoading?.root?.isVisible = it.refresh is LoadState.Loading
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val users = userListAdapter.snapshot().items
        viewModel.saveData(users)
    }

    override fun onUserClick(user: UserBriefModel) {
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
    }

    override fun retryLoading() {
        userListAdapter.retry()
    }
}