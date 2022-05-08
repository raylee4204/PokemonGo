package city.samaritan.pokemongo.di

import android.content.Context
import androidx.room.Room
import city.samaritan.pokemongo.db.AppDatabase
import city.samaritan.pokemongo.network.AuthRepository
import city.samaritan.pokemongo.network.OpenPokemonApiService
import city.samaritan.pokemongo.network.PokemonRepository
import city.samaritan.pokemongo.network.PokemonService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesPokemonRepository(
        service: PokemonService,
        openPokemonApiService: OpenPokemonApiService,
        database: AppDatabase
    ) = PokemonRepository(service, openPokemonApiService, database.pokemonDao())

    @Singleton
    @Provides
    fun providesAuthRepository(service: PokemonService) = AuthRepository(service)

    @Singleton
    @Provides
    fun providesAppDatabase(@ApplicationContext applicationContext: Context): AppDatabase {
        return Room.databaseBuilder(applicationContext, AppDatabase::class.java, "pokemon_db")
            .build()
    }
}