package com.mrfourfour.ichi.friendship.application

import com.mrfourfour.ichi.friendship.domain.user.User
import com.mrfourfour.ichi.friendship.domain.user.UserRepository
import com.mrfourfour.ichi.user.domain.UserCreated
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class UserCreatedEventHandler(
    private val userRepository: UserRepository
) {

    @EventListener
    fun handle(userCreated: UserCreated) = GlobalScope.launch(Dispatchers.IO) {
        val userId = userCreated.id.value
        userRepository.save(User(userId))
    }
}