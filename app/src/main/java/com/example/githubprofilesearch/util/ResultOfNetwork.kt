package com.example.githubprofilesearch.util

enum class Status {
    SUCCESS,
    ERROR
}

data class ResultOfNetwork<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): ResultOfNetwork<T> {
            return ResultOfNetwork(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): ResultOfNetwork<T> {
            return ResultOfNetwork(Status.ERROR, data, msg)
        }
    }
}