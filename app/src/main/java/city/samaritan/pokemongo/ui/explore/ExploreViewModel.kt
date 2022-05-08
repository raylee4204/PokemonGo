package city.samaritan.pokemongo.ui.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import city.samaritan.pokemongo.model.Pokemon
import city.samaritan.pokemongo.network.OpenPokemonRepository
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class ExploreViewModel @Inject constructor(private val repository: OpenPokemonRepository) :
    ViewModel() {

    private val _randomPokemonLocations = MutableLiveData<List<LatLng>>()
    val randomPokemonLocations = _randomPokemonLocations
    private val random = Random(System.currentTimeMillis())

    fun getRandomLocations(bounds: LatLngBounds) {
        _randomPokemonLocations.value = emptyList()
        val newLocations = ArrayList<LatLng>()
        val minLat = bounds.northeast.latitude
        val minLng = bounds.northeast.longitude
        val maxLat = bounds.southwest.latitude
        val maxLng = bounds.southwest.longitude

        viewModelScope.launch(Dispatchers.Default) {
            for (i in 0 until MAX_POKEMON_COUNT) {
                val randomLatLng = LatLng(
                    random.nextDouble() * (maxLat - minLat) + minLat,
                    random.nextDouble() * (maxLng - minLng) + minLng
                )
                newLocations.add(randomLatLng)
            }
            _randomPokemonLocations.postValue(newLocations)
        }
    }

    companion object {
        private const val MAX_POKEMON_COUNT = 5
    }

}