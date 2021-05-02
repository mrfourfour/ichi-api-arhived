package com.mrfourfour.ichi.common.domain

interface EventPublisher {
    fun <T> publish(domainEvent: DomainEvent<T>)
}