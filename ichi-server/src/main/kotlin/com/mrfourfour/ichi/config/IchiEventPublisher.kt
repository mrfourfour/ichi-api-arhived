package com.mrfourfour.ichi.config

import com.mrfourfour.ichi.common.domain.DomainEvent
import com.mrfourfour.ichi.common.domain.EventPublisher
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class IchiEventPublisher(
    private val applicationEventPublisher: ApplicationEventPublisher
) : EventPublisher {

    override fun <T> publish(domainEvent: DomainEvent<T>) {
        applicationEventPublisher.publishEvent(domainEvent)
    }
}