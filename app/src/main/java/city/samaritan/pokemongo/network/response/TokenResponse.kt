package city.samaritan.pokemongo.network.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TokenResponse(val token: String, val expiresAt: Long)