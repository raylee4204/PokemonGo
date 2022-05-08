package city.samaritan.pokemongo.network

import city.samaritan.pokemongo.model.Token
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val service: PokemonService
) {
    private var token: Token? = null

    suspend fun getToken(): Token? {
        validateToken()
        return token
    }

    private suspend fun validateToken() {
        val expiredTime = token?.expiresAt
        if (expiredTime == null) {
            token = refreshToken()
        } else if (expiredTime.isAfter(LocalDateTime.now())) {
            token = refreshToken()
        }
    }

    private suspend fun refreshToken(): Token? {
        return withContext(Dispatchers.Default) {
            try {
                val response = service.generateToken()
                Token(
                    response.token,
                    LocalDateTime.ofInstant(
                        Instant.ofEpochMilli(response.expiresAt),
                        ZoneId.systemDefault()
                    )
                )
            } catch (e: IOException) {
                null
            }
        }
    }
}