package city.samaritan.pokemongo.network.utils

import city.samaritan.pokemongo.network.AuthRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class AuthInterceptor @Inject constructor(private val repo: dagger.Lazy<AuthRepository>) :
    Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val token = runBlocking { repo.get().getToken() } ?: return null
        return response.request
            .newBuilder()
            .header("Authorization", "Bearer ${token.token}")
            .build()
    }
}