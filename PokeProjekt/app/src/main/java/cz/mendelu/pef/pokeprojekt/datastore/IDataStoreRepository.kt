package cz.mendelu.pef.pokeprojekt.datastore

import cz.mendelu.pef.pokeprojekt.database.PokemonRoom
import cz.mendelu.pef.pokeprojekt.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface IDataStoreRepository {
    suspend fun setPokemonToCatch(pokemon: PokemonRoom)
    fun getPokemonToCatch(): Flow<PokemonRoom>
    suspend fun setFavoritePokemonId(id: Long)
    suspend fun getFavoritePokemonId(): Long?

    suspend fun setSharedPokemon(string: String)
    suspend fun getSharedPokemon(): String?
}