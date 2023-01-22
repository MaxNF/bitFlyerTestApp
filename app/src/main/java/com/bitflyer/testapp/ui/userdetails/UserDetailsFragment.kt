package com.bitflyer.testapp.ui.userdetails

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import coil.transform.CircleCropTransformation
import com.bitflyer.testapp.R
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
        binding?.detailsContent?.nestedScroll?.setOnApplyWindowInsetsListener { view, windowInsets ->
            val bottomInset = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                windowInsets.getInsets(WindowInsets.Type.systemBars()).bottom
            } else {
                windowInsets.stableInsetBottom
            }
            view.updatePadding(bottom = bottomInset)
            windowInsets
        }

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
            it.name.isVisible = !model.name.isNullOrEmpty()
            it.name.text = model.name
            it.login.text = model.login
            it.followersValue.text = model.followers
            it.followingValue.text = model.following

            it.blogLabel.isVisible = !model.blogUrl.isNullOrEmpty()
            it.blogValue.isVisible = !model.blogUrl.isNullOrEmpty()
            it.blogValue.text = model.blogUrl

            it.emailLabel.isVisible = !model.email.isNullOrEmpty()
            it.emailValue.isVisible = !model.email.isNullOrEmpty()
            it.emailValue.text = model.email

            it.companyLabel.isVisible = !model.company.isNullOrEmpty()
            it.companyValue.isVisible = !model.company.isNullOrEmpty()
            it.companyValue.text = model.company

            it.locationLabel.isVisible = !model.location.isNullOrEmpty()
            it.locationValue.isVisible = !model.location.isNullOrEmpty()
            it.locationValue.text = model.location

            it.twitterLabel.isVisible = !model.twitter.isNullOrEmpty()
            it.twitterValue.isVisible = !model.twitter.isNullOrEmpty()
            it.twitterValue.text = getString(R.string.twitter_template, model.twitter)
            it.twitterValue.paintFlags = it.twitterValue.paintFlags or Paint.UNDERLINE_TEXT_FLAG
            it.twitterValue.setOnClickListener { openTwitterAccount(model.twitter) }

            it.totalReposValue.text = model.repos
        }
        setDividersVisibility(model)
    }

    private fun setDividersVisibility(model: UserDetailsModel) {
        val isBottomDividerVisible = !model.blogUrl.isNullOrEmpty() ||
                !model.twitter.isNullOrEmpty() ||
                !model.location.isNullOrEmpty() ||
                !model.company.isNullOrEmpty() ||
                !model.email.isNullOrEmpty()
        binding?.detailsContent?.bottomDivider?.isVisible = isBottomDividerVisible
    }

    private fun openTwitterAccount(userName: String?) {
        if (userName == null) return
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/$userName"))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}