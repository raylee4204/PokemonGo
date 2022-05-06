package city.samaritan.pokemongo.network

import city.samaritan.pokemongo.network.response.TokenResponse
import retrofit2.http.POST
import retrofit2.http.Query

interface PokemonService {

    @POST("token")
    suspend fun generateToken(@Query("email") email: String = "raylee4204@gmail.com"): TokenResponse
}