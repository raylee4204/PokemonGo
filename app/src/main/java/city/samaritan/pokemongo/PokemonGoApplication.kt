package city.samaritan.pokemongo

import android.app.Application
import city.samaritan.pokemongo.network.PokemonRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class PokemonGoApplication: Application() {

    @Inject lateinit var repository: PokemonRepository

    override fun onCreate() {
        super.onCreate()
        GlobalScope.launch(Dispatchers.Default) {
            repository.getPokemons()
        }
    }
}