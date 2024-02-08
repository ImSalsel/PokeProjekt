package cz.mendelu.pef.pokeprojekt.fake

import cz.mendelu.pef.pokeprojekt.database.IPokemonRepository
import cz.mendelu.pef.pokeprojekt.database.PokemonRoom
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FakePokemonRepository @Inject constructor() : IPokemonRepository {
    private val mockPokemons = mutableListOf<PokemonRoom>()

    init {
        mockPokemons.add(
            PokemonRoom(
                id = 1,
                pokemonId = 1,
                name = "bulbasaur",
                height = 7,
                weight = 69,
                moves = "474,104,803,33",
                frontDefaultSprite = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
                types = "grass, poison",
                hp = 15,
                level = 5,
                xp = 0.0F
            )
        )


    }


    override fun getAll(): Flow<List<PokemonRoom>> {
        return flow { emit(mockPokemons) }
    }

    override suspend fun insert(pokemon: PokemonRoom) {
        mockPokemons.add(pokemon)
    }

    override suspend fun getPokemonById(id: Int): PokemonRoom {
        return mockPokemons.first { it.id?.toInt() == id }
    }


}