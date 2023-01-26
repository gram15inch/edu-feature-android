package com.example.networkhandle

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.networkhandle.base.ErrorHandleViewModel
import com.example.networkhandle.data.model.RemoteUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository) :
    ErrorHandleViewModel() {
    private val _userListFlow: MutableStateFlow<List<RemoteUser>> = MutableStateFlow(emptyList())
    val userList: LiveData<List<RemoteUser>> get() = _userListFlow.asLiveData()

    init {
        viewModelScope.launch {
            userRepository.getUsers("test")
                .apply {
                    Timber.tag("response error").d("v code: ${this.code()}")
                    Timber.tag("response error").d("v size: ${this.body()?.remoteUsers?.size ?: 0}")
                    _userListFlow.emit(this.body()!!.remoteUsers)
                }
        }
    }
}