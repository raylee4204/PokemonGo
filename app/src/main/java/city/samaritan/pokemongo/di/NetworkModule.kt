package city.samaritan.pokemongo.di

import city.samaritan.pokemongo.network.OpenPokemonApiService
import city.samaritan.pokemongo.network.PokemonRepository
import city.samaritan.pokemongo.network.PokemonService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofitBuilder(
        moshi: Moshi
    ) = Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi))

    @Singleton
    @Provides
    fun provideMoshi() = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

    @Singleton
    @Provides
    fun providePokemonService(
        retrofitBuilder: Retrofit.Builder,
        @AuthInterceptorOkHttpClient okHttpClient: OkHttpClient
    ): PokemonService {
        return retrofitBuilder
            .baseUrl("http://us-central1-samaritan-android-assignment.cloudfunctions.net/")
            .client(okHttpClient)
            .build()
            .create(PokemonService::class.java)
    }

    @Singleton
    @Provides
    fun provideOpenPokemonApiService(
        retrofitBuilder: Retrofit.Builder,
        @OtherInterceptorOkHttpClient okHttpClient: OkHttpClient
    ): OpenPokemonApiService {
        return retrofitBuilder
            .baseUrl("https://pokeapi.co/api/v2/")
            .client(okHttpClient)
            .build()
            .create(OpenPokemonApiService::class.java)
    }

    @AuthInterceptorOkHttpClient
    @Singleton
    @Provides
    fun provideAuthOkhttp(
        repository: PokemonRepository
    ): OkHttpClient {
        return OkHttpClient.Builder().authenticator { _, response ->
            return@authenticator runBlocking(Dispatchers.Default) {
                val token = repository.getToken() ?: return@runBlocking null

                return@runBlocking response.request
                    .newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
            }
        }.build()
    }

    @OtherInterceptorOkHttpClient
    @Singleton
    @Provides
    fun provideOkhttp() = OkHttpClient.Builder().build()

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AuthInterceptorOkHttpClient

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class OtherInterceptorOkHttpClient
}