package com.pinto.mymovieappkt.domain.model

data class AuthResponse(
    val expires_at: String,
    val guest_session_id: String,
    val success: Boolean
)