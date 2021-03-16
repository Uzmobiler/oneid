package uz.mobiler.oneid.data.models

data class OneIdResponce(
    val access_token: String,
    val expires_in: Long,
    val refresh_token: String,
    val scope: String,
    val token_type: String
)