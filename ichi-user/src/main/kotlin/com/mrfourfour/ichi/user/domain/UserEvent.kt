package com.mrfourfour.ichi.user.domain

import com.mrfourfour.ichi.common.domain.DomainEvent

sealed class UserEvent : DomainEvent<UserId>

class UserCreated(override val id: UserId) : UserEvent()
