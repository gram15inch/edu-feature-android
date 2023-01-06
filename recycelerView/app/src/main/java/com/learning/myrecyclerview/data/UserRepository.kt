package com.learning.myrecyclerview.data

import com.learning.myrecyclerview.domain.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class UserRepository @Inject constructor() {
    var count =3
    suspend fun getUserList():List<User>{
        val list = mutableListOf<User>()
        withContext(Dispatchers.IO) {
            Thread.sleep(1000)
            val size = count++
            repeat(size){
                list.add(
                    User(
                        it,
                        "name$it",
                        it+10,
                        "email:$it"
                    )
                )
            }
            Timber.tag("timber").d("repository ${list.size}")

        }
        return list
    }
}