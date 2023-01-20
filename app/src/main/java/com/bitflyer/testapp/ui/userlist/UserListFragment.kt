package com.bitflyer.testapp.ui.userlist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bitflyer.testapp.R
import com.bitflyer.testapp.databinding.FragmentUserListBinding
import com.bitflyer.testapp.domain.userlist.entity.UserBriefEntity
import com.bitflyer.testapp.ui.BaseMapper
import com.bitflyer.testapp.ui.userlist.adapter.OnUserClickListener
import com.bitflyer.testapp.ui.userlist.adapter.UserListAdapter
import com.bitflyer.testapp.ui.userlist.model.UserBriefModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class UserListFragment : Fragment(), OnUserClickListener {

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
            Log.d(TAG, "onViewCreated: $it")
            userListAdapter = UserListAdapter(this, mapper)
            it.adapter = userListAdapter
            it.layoutManager = LinearLayoutManager(context)
        }
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
//                isLoading = it.append is LoadState.Loading
//                if (!isLoading) binding?.progressBar?.visibility = View.GONE
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
}