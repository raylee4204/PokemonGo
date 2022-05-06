package city.samaritan.pokemongo.network

import city.samaritan.pokemongo.model.Pokemon
import city.samaritan.pokemongo.network.dto.toModel
import java.io.IOException
import javax.inject.Inject

class OpenPokemonRepository @Inject constructor(private val service: OpenPokemonApiService) {

    suspend fun getPokemons(): List<Pokemon> {
        return try {
            val response = service.getPokemons()
            response.results.mapNotNull {
                try {
                    val result = service.getPokemonDetail(it.url)
                    result.toModel()
                } catch (e: IOException) {
                    null
                }
            }
        } catch (exception: IOException) {
            emptyList()
        }
    }
}