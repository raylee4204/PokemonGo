package city.samaritan.pokemongo.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import city.samaritan.pokemongo.model.Pokemon
import city.samaritan.pokemongo.network.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(private val repository: PokemonRepository) :
    ViewModel() {
    private val _pokemon = MutableLiveData<Pokemon>()
    val pokemon: LiveData<Pokemon> = _pokemon

    fun getPokemonById(pokemonId: Int) {
        viewModelScope.launch(Dispatchers.Default) {
            _pokemon.postValue(repository.getPokemonDetailById(pokemonId))
        }
    }

}