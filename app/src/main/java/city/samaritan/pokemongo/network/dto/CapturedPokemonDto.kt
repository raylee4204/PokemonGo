package city.samaritan.pokemongo.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CapturedPokemonDto(
    val id: Int,
    val name: String,
    @Json(name = "captured_at") val capturedDate: String,
    @Json(name = "captured_lat_at") val latitude: Double,
    @Json(name = "captured_long_at") val longitude: Double
)