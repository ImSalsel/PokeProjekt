package cz.mendelu.pef.pokeprojekt

import cz.mendelu.pef.pokeprojekt.architecture.CommunicationResult
import cz.mendelu.pef.pokeprojekt.communication.pokemon.PokemonAPI
import cz.mendelu.pef.pokeprojekt.communication.pokemon.PokemonRemoteRepositoryImpl
import cz.mendelu.pef.pokeprojekt.model.Pokemon
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class PokemonRemoteRepositoryImplTest {

    private lateinit var pokemonAPI: PokemonAPI
    private lateinit var repository: PokemonRemoteRepositoryImpl

    @Before
    fun setUp() {
        pokemonAPI = mockk()
        repository = PokemonRemoteRepositoryImpl(pokemonAPI)
    }

    @Test
    fun `test getPokemonById success`() = runBlocking {
        val pokemonId = 1L
        val expectedResponse = mockk<CommunicationResult<Pokemon>>()
        val expectedResult = mockk<Response<Pokemon>>()

        coEvery { pokemonAPI.getPokemonById(pokemonId) } returns expectedResult

        val result = repository.getPokemonById(pokemonId)

        assertEquals(expectedResponse, result)
        verify { runBlocking {pokemonAPI.getPokemonById(pokemonId) }}
    }



}
