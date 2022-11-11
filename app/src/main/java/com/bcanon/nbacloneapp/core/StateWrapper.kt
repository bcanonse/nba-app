package com.bcanon.nbacloneapp.core

data class StateWrapper<T>(
    val isLoading: Boolean = true,
    val data: T? = null,
    val error: String? = null
)
