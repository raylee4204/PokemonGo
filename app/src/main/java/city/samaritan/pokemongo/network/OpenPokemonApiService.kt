package city.samaritan.pokemongo.network

import city.samaritan.pokemongo.network.dto.PokemonDto
import city.samaritan.pokemongo.network.response.PokemonsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface OpenPokemonApiService {

    @GET("pokemon/")
    suspend fun getPokemons(@Query("limit") limit: Int = 50): PokemonsResponse

    @GET
    suspend fun getPokemonDetail(@Url url: String): PokemonDto

    @GET("pokemon/{id}/")
    suspend fun getPokemonDetailById(@Path("id") id: Int): PokemonDto
}