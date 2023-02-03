package com.bitflyer.testapp.ui.userdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitflyer.testapp.data.CallResult
import com.bitflyer.testapp.data.userdetails.dto.UserDetails
import com.bitflyer.testapp.domain.repository.UserDetailsRepository
import com.bitflyer.testapp.ui.BaseMapper
import com.bitflyer.testapp.ui.userdetails.model.UserDetailsModel
import com.bitflyer.testapp.ui.userdetails.state.UserDetailsScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    private val repository: UserDetailsRepository,
    private val mapper: BaseMapper<UserDetails, UserDetailsModel>,
    private val state: SavedStateHandle
) : ViewModel() {
    companion object {
        private const val USER_DETAILS_KEY = "user_details"
    }

    private val _screenState = MutableLiveData<UserDetailsScreenState>()
    val screenState: LiveData<UserDetailsScreenState> = _screenState

    fun fetchDetails(login: String) {
        val savedState: UserDetailsModel? = state[USER_DETAILS_KEY]
        if (savedState != null) _screenState.value = UserDetailsScreenState.Loaded(savedState)
        else {
            _screenState.value = UserDetailsScreenState.Loading
            viewModelScope.launch {
                when (val userDetails = repository.getUserDetails(login)) {
                    is CallResult.Success -> {
                        val model = mapper.map(userDetails.value)
                        _screenState.value = UserDetailsScreenState.Loaded(model)
                        state[USER_DETAILS_KEY] = model
                    }
                    else -> _screenState.value = UserDetailsScreenState.Error
                }
            }
        }
    }
}