package com.mrfourfour.ichi.common.domain

interface DomainEvent<I> {
    val id: I
}