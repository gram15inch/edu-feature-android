package com.example.networkhandle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.networkhandle.base.ErrorHandleViewModel
import com.example.networkhandle.data.model.signup.RemoteSignUp
import com.example.networkhandle.data.model.signup.SignUpRequest
import com.example.networkhandle.data.model.user.RemoteUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository) :
    ErrorHandleViewModel() {
    private val _userListFlow: MutableStateFlow<List<RemoteUser>> = MutableStateFlow(emptyList())
    val userList: LiveData<List<RemoteUser>> get() = _userListFlow.asLiveData()
    private val _signUpFlow: MutableStateFlow<RemoteSignUp?> = MutableStateFlow(null)
    val signUp: LiveData<RemoteSignUp?> get() = _signUpFlow.asLiveData()

    private val _loadingState = MutableLiveData(false)
    val loadingState get() = _loadingState

    init {
        refreshUser()
    }

    val request = SignUpRequest(
        "drjart@aa.bb",
        "1234",
        "1234",
        "drjart",
        "010-000-000",
    )
    fun refreshUser(){
        viewModelScope.launch(exceptionHandler) {
            userRepository.getUsers("test")
                .apply {
                    if(this.isSuccessful)
                        _userListFlow.emit(this.body()!!.remoteUsers)
                    else
                        Timber.tag("response error").d("getUser false")
                }
        }
    }

    fun resistUser(request: SignUpRequest) {
        viewModelScope.launch(exceptionHandler) {
            userRepository.postUsers(request)
                .apply {
                    if(this.isSuccessful)
                        _signUpFlow.emit(this.body()!!.remoteSignUp)
                    else
                        Timber.tag("response error").d("postUser false")
                }
        }
    }
}