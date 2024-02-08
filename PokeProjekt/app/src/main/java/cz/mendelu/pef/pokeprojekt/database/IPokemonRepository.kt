package cz.mendelu.pef.pokeprojekt.database

import kotlinx.coroutines.flow.Flow

interface IPokemonRepository {

    fun getAll(): Flow<List<PokemonRoom>>
    suspend fun insert(pokemon: PokemonRoom)
    suspend fun getPokemonById(id: Int): PokemonRoom

}