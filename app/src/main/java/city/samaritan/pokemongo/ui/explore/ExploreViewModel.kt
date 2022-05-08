package city.samaritan.pokemongo.ui.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import city.samaritan.pokemongo.model.Pokemon
import city.samaritan.pokemongo.model.PokemonLocation
import city.samaritan.pokemongo.network.PokemonRepository
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class ExploreViewModel @Inject constructor(private val repository: PokemonRepository) :
    ViewModel() {

    private val _pokemons = MutableLiveData<List<Pokemon>>()
    val pokemons: LiveData<List<Pokemon>> = _pokemons

    private val _randomPokemonLocations = MutableLiveData<List<PokemonLocation>>()
    val randomPokemonLocations = _randomPokemonLocations
    private val random = Random(System.currentTimeMillis())

    init {
        viewModelScope.launch(Dispatchers.Default) {
            _pokemons.postValue(repository.getPokemons())
        }
    }

    fun getRandomLocations(bounds: LatLngBounds) {
        if (_pokemons.value == null) return

        _randomPokemonLocations.value = emptyList()
        val allPokemons = ArrayList(_pokemons.value!!)
        val newLocations = ArrayList<PokemonLocation>()

        val minLat = bounds.northeast.latitude
        val minLng = bounds.northeast.longitude
        val maxLat = bounds.southwest.latitude
        val maxLng = bounds.southwest.longitude

        viewModelScope.launch(Dispatchers.Default) {
            for (i in 0 until MAX_POKEMON_COUNT) {
                if (allPokemons.isEmpty()) break
                val randomLatLng = LatLng(
                    random.nextDouble() * (maxLat - minLat) + minLat,
                    random.nextDouble() * (maxLng - minLng) + minLng
                )
                val randomPokemon = allPokemons.removeAt(random.nextInt(allPokemons.size))
                newLocations.add(PokemonLocation(randomPokemon, randomLatLng))
            }
            _randomPokemonLocations.postValue(newLocations)
        }
    }

    companion object {
        private const val MAX_POKEMON_COUNT = 5
    }

}