package cz.mendelu.pef.pokeprojekt.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface PokemonsDao {

    @Query("SELECT * FROM pokemon_table")
    fun getAll(): Flow<List<PokemonRoom>>

    @Insert
    suspend fun insert(pokemon: PokemonRoom)

    @Query("SELECT * FROM pokemon_table WHERE id = :id")
    suspend fun getPokemonById(id: Int): PokemonRoom



}