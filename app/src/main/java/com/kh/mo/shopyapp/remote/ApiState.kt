package com.kh.mo.shopyapp.remote

sealed interface ApiState<out T> {
    class Success<T>(val data : T):ApiState<T>
    class Failure(val msg:String):ApiState<Nothing>
    object Loading:ApiState<Nothing>

    fun toData(): T? = if (this is Success) data else null
}