package com.example.taskmngmtapp.utils


sealed class resource<T>(val status:Status, val data: T? = null, val message : String? = null) {

    class Success<T>(message: String,data: T?= null) : resource<T>(Status.SUCCESS,data,message)
    class Error<T>(message: String,data: T? = null) : resource<T>(Status.ERROR,data,message)
    class Loading<T> : resource<T>(Status.LOADING)

}