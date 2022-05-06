package city.samaritan.pokemongo.network.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonsResponse(val count: Int, val results: List<PokemonRef>) {
    @JsonClass(generateAdapter = true)
    data class PokemonRef(val name: String, val url: String)
}