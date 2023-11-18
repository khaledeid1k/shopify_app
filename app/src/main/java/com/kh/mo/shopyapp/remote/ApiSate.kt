package com.kh.mo.shopyapp.remote

sealed interface ApiSate<out T> {
    class Success<T>(val data : T):ApiSate<T>
    class Failure(val msg:String):ApiSate<Nothing>
    object Loading:ApiSate<Nothing>

    fun toData(): T? = if (this is Success) data else null
}