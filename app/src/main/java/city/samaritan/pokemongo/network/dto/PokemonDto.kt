package city.samaritan.pokemongo.network.dto

import city.samaritan.pokemongo.model.Pokemon
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonDto(val id: Int, val name: String, val sprites: Sprites, val stats: List<Stat>) {

    data class Sprites(val other: OtherSprites) {

        data class OtherSprites(@Json(name = "official-artwork") val officialArtWorkData: OfficialArtWork) {
            data class OfficialArtWork(@Json(name = "front_default") val defaultFront: String)
        }

        fun getDefaultOfficialArt() = other.officialArtWorkData.defaultFront
    }

    data class Stat(
        @Json(name = "base_stat") val baseStat: Int,
        @Json(name = "stat") val statData: StatData
    ) {
        data class StatData(val name: String)

        fun getName() = statData.name
    }
}

fun PokemonDto.toModel(): Pokemon {
    return Pokemon(
        id = id,
        name = name,
        defaultFrontImage = sprites.getDefaultOfficialArt(),
        stats = stats.map { Pokemon.Stat(name = it.getName(), base = it.baseStat) }
    )
}