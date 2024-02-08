package cz.mendelu.pef.pokeprojekt


import cz.mendelu.pef.pokeprojekt.database.PokemonRepositoryImpl
import cz.mendelu.pef.pokeprojekt.database.PokemonRoom
import cz.mendelu.pef.pokeprojekt.database.PokemonsDao
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class RepositoryTest {

    private lateinit var pokemonsDao: PokemonsDao
    private lateinit var repository: PokemonRepositoryImpl

    @Before
    fun setUp() {
        pokemonsDao = mockk()
        repository = PokemonRepositoryImpl(pokemonsDao)
    }

    @Test
    fun `getAll returns flow of data from dao`() {

        val pokemonRoom = PokemonRoom(id=1, pokemonId=1, name="bulbasaur", height=7, weight=69, moves="474,104,803,33", frontDefaultSprite="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png", types="grass, poison", hp=15, level=5, xp=0.0F)

        val expectedData = flowOf(listOf(pokemonRoom))
        coEvery { pokemonsDao.getAll() } returns expectedData

        val result = repository.getAll()

        assertEquals(expectedData, result)
    }

    @Test
    fun `insert calls dao with correct pokemon`() = runBlocking {
        val pokemonRoom = PokemonRoom(id=1, pokemonId=1, name="bulbasaur", height=7, weight=69, moves="474,104,803,33", frontDefaultSprite="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png", types="grass, poison", hp=15, level=5, xp=0.0F)


        coEvery { pokemonsDao.insert(pokemonRoom) } returns Unit

        repository.insert(pokemonRoom)

        coVerify { pokemonsDao.insert(pokemonRoom) }
    }

    @Test
    fun `getPokemonById returns pokemon from dao`() = runBlocking {

        val expectedPokemon = PokemonRoom(id=1, pokemonId=1, name="bulbasaur", height=7, weight=69, moves="474,104,803,33", frontDefaultSprite="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png", types="grass, poison", hp=15, level=5, xp=0.0F)

        val id = 1
        coEvery { pokemonsDao.getPokemonById(id) } returns expectedPokemon

        val result = repository.getPokemonById(id)

        assertEquals(expectedPokemon, result)
    }
}
