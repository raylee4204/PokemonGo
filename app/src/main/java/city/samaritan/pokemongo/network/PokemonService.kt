package city.samaritan.pokemongo.network

import city.samaritan.pokemongo.network.response.TokenResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PokemonService {

    @GET("https://pokeapi.co/api/v2/ability/")
    suspend fun getPokemons(@Query("limit") limit: Int = 20)

    @POST("/token")
    suspend fun generateToken(@Query("email") email: String = "raylee4204@gmail.com"): TokenResponse
}