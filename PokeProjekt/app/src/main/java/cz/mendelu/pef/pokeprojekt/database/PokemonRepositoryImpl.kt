package cz.mendelu.pef.pokeprojekt.database

import kotlinx.coroutines.flow.Flow

class PokemonRepositoryImpl(private val pokemonsDao: PokemonsDao) : IPokemonRepository {
    override fun getAll(): Flow<List<PokemonRoom>> {
        return pokemonsDao.getAll()
    }

    override suspend fun insert(pokemon: PokemonRoom) {
        return pokemonsDao.insert(pokemon)
    }

    override suspend fun getPokemonById(id: Int): PokemonRoom {
        return pokemonsDao.getPokemonById(id)
    }

}