package com.learning.myrecyclerview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learning.myrecyclerview.data.UserRepository
import com.learning.myrecyclerview.domain.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

enum class UiStatus { LOADING, ERROR, DONE }
data class UiUser(val id: Int, val name: String, val age: Int)

@HiltViewModel
class MainViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
    private val _status = MutableLiveData<UiStatus>()
    val status: LiveData<UiStatus> get() = _status
    private val _uiUserList = MutableLiveData<List<UiUser>>()
    val uiUserList: LiveData<List<UiUser>> get() = _uiUserList

    init {
        refreshViewModel()
        Timber.tag("timber").d("init")
    }

    fun refreshViewModel() {
        viewModelScope.launch {
            _status.value = UiStatus.LOADING
            try {
                val users = userRepository.getUserList()
                val maps = users.map {
                    UiUser(
                        it.id,
                        it.name,
                        it.age
                    )
                }
                Timber.tag("timber").d("viewModel ${maps.size}")
                _uiUserList.value = maps

                _status.value = UiStatus.DONE
            } catch (_: Exception) {
                _status.value = UiStatus.ERROR
            }

        }

    }

}