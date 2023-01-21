package com.bitflyer.testapp.ui.userdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import coil.transform.CircleCropTransformation
import com.bitflyer.testapp.databinding.FragmentUserDetailsBinding
import com.bitflyer.testapp.ui.userdetails.model.UserDetailsModel
import com.bitflyer.testapp.ui.userdetails.state.UserDetailsScreenState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailsFragment : Fragment() {
    companion object {
        private const val TAG = "UserListFragment"
    }

    private val viewModel: UserDetailsViewModel by viewModels()
    private var binding: FragmentUserDetailsBinding? = null
    val args: UserDetailsFragmentArgs by navArgs()
    private lateinit var login: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        login = args.login
        viewModel.fetchDetails(login)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserDetailsBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//            .setOnApplyWindowInsetsListener { view, windowInsets ->
//                val bottomInset = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R){
//                    windowInsets.getInsets(WindowInsets.Type.systemBars()).bottom
//                } else{
//                    windowInsets.stableInsetBottom
//                }
//                view.updatePadding(bottom = bottomInset)
//                windowInsets
//            }

        binding?.loadingError?.tryAgain?.setOnClickListener { viewModel.fetchDetails(login) }
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.screenState.observe(viewLifecycleOwner) { state ->
            binding?.loadingError?.root?.isVisible = state is UserDetailsScreenState.Error
            binding?.detailsLoading?.root?.isVisible = state is UserDetailsScreenState.Loading
            binding?.detailsContent?.root?.isVisible = state is UserDetailsScreenState.Loaded

            if (state is UserDetailsScreenState.Loaded) {
                bindModel(state.model)
            }
        }
    }

    private fun bindModel(model: UserDetailsModel) {
        binding?.detailsContent?.let {
            it.avatar.load(model.avatarUrl) {
                transformations(CircleCropTransformation())
            }
            it.name.isVisible = model.name != null
            it.name.text = model.name
            it.login.text = model.login
            it.followersValue.text = model.followers
            it.followingValue.text = model.following

            it.blogLabel.isVisible = model.blogUrl != null
            it.blogValue.isVisible = model.blogUrl != null
            it.blogValue.text = model.blogUrl

            it.emailLabel.isVisible = model.email != null
            it.emailValue.isVisible = model.email != null
            it.emailValue.text = model.email

            it.companyLabel.isVisible = model.company != null
            it.companyValue.isVisible = model.company != null
            it.companyValue.text = model.company

            it.locationLabel.isVisible = model.location != null
            it.locationValue.isVisible = model.location != null
            it.locationValue.text = model.location

            it.twitterLabel.isVisible = model.twitter != null
            it.twitterValue.isVisible = model.twitter != null
            it.twitterValue.text = model.twitter

            it.totalReposValue.text = model.repos
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}