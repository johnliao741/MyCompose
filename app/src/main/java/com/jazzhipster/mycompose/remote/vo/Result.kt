package com.jazzhipster.mycompose.remote.vo

sealed class Result<out R> {
    object Initial:Result<Nothing>()
    object Loading:Result<Nothing>()
    data class Success<T>(val data:T):Result<T>()
    data class Error(val e : Throwable) :Result<Nothing>()
}
