package city.samaritan.pokemongo.network

import android.util.Log
import city.samaritan.pokemongo.db.PokemonDao
import city.samaritan.pokemongo.model.Pokemon
import city.samaritan.pokemongo.network.dto.toModel
import city.samaritan.pokemongo.toLocalDateTime
import com.google.android.gms.maps.model.LatLng
import java.io.IOException
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val service: PokemonService,
    private val openPokemonApiService: OpenPokemonApiService,
    private val dao: PokemonDao
) {

    suspend fun getCapturedPokemons(): List<Pokemon> {
        try {
            val capturedPokemons = service.getCapturedPokemons()
            for (pokemon in capturedPokemons) {
                val localPokemon = dao.findPokemonById(pokemon.id) ?: continue

                localPokemon.capturedDate = pokemon.capturedDate.toLocalDateTime()
                localPokemon.capturedLocation = LatLng(pokemon.latitude, pokemon.longitude)
                dao.updatePokemon(localPokemon)
            }
        } catch (e: IOException) {
        }
        return dao.getCapturedPokemons()
    }

    suspend fun getPokemons(): List<Pokemon> {
        return try {
            val response = openPokemonApiService.getPokemons()
            val result = response.results.mapNotNull {
                try {
                    val pokemonWithDetail = openPokemonApiService.getPokemonDetail(it.url)
                    pokemonWithDetail.toModel()
                } catch (e: IOException) {
                    null
                }
            }
            dao.insertPokemons(result)
            dao.getCapturedPokemons()
        } catch (exception: IOException) {
            emptyList()
        }
    }

    suspend fun getPokemonDetailById(id: Int): Pokemon? {
        try {
            val pokemon = openPokemonApiService.getPokemonDetailById(id).toModel()
            dao.updatePokemon(pokemon)
        } catch (exception: IOException) {
        }

        return dao.findPokemonById(id)
    }
}