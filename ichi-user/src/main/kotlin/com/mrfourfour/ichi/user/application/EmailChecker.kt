package com.mrfourfour.ichi.user.application

interface EmailChecker {
    abstract fun checkDuplicate(email: String)

}
