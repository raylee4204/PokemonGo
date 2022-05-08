package city.samaritan.pokemongo.db

import androidx.room.*
import city.samaritan.pokemongo.model.Pokemon

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemons(pokemons: List<Pokemon>)

    @Query("SELECT * FROM " + Pokemon.TABLE_NAME)
    suspend fun getPokemons(): List<Pokemon>

    @Query("SELECT * FROM " + Pokemon.TABLE_NAME + " WHERE captured_date NOT NULL")
    suspend fun getCapturedPokemons(): List<Pokemon>

    @Query("SELECT * FROM " + Pokemon.TABLE_NAME + " WHERE id = :id")
    suspend fun findPokemonById(id: Int): Pokemon?

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updatePokemon(pokemon: Pokemon)
}