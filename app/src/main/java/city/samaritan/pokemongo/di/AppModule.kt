package city.samaritan.pokemongo.di

import city.samaritan.pokemongo.network.OpenPokemonApiService
import city.samaritan.pokemongo.network.OpenPokemonRepository
import city.samaritan.pokemongo.network.PokemonRepository
import city.samaritan.pokemongo.network.PokemonService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesPokemonRepository(service: PokemonService) = PokemonRepository(service)

    @Singleton
    @Provides
    fun providesOpenPokemonRepository(service: OpenPokemonApiService) = OpenPokemonRepository(service)
}