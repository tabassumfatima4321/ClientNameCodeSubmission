package com.apex.codeassesment

data class ApiResponse<T>(
    val results: T?,
    val error: String? = null
)
