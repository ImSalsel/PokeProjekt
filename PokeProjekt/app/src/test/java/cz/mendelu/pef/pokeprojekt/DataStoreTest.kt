package cz.mendelu.pef.pokeprojekt


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.preferencesOf
import cz.mendelu.pef.pokeprojekt.datastore.DataStoreRepositoryImpl
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import androidx.test.core.app.ApplicationProvider
import androidx.datastore.preferences.core.Preferences
import cz.mendelu.pef.pokeprojekt.database.PokemonRoom
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DataStoreRepositoryImplTest {

    private lateinit var dataStoreRepositoryImpl: DataStoreRepositoryImpl
    private lateinit var dataStore: DataStore<Preferences>

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        dataStore = mockk()
        dataStoreRepositoryImpl = DataStoreRepositoryImpl(context)
    }

    @Test
    fun `set and get Pokemon correctly`() = runTest {
        // Arrange
        val testPokemon = PokemonRoom(id=1, pokemonId=1, name="bulbasaur", height=7, weight=69, moves="474,104,803,33", frontDefaultSprite="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png", types="grass, poison", hp=15, level=5, xp=0.0F)


        // Act
        dataStoreRepositoryImpl.setPokemonToCatch(testPokemon)
        val retrievedPokemon = dataStoreRepositoryImpl.getPokemonToCatch().first()

        // Assert
        assertEquals(testPokemon, retrievedPokemon)
    }

    @Test
    fun `set and get favorite Pokemon ID correctly`() = runTest {

        val favoritePokemonId = 1L


        dataStoreRepositoryImpl.setFavoritePokemonId(favoritePokemonId)
        val retrievedId = dataStoreRepositoryImpl.getFavoritePokemonId()

        // Assert
        assertEquals(favoritePokemonId, retrievedId)
    }

    @Test
    fun `set and get shared Pokemon correctly`() = runTest {
        // Arrange
        val sharedPokemonString = "Pikachu"

        dataStoreRepositoryImpl.setSharedPokemon(sharedPokemonString)
        val retrievedString = dataStoreRepositoryImpl.getSharedPokemon()

        // Assert
        assertEquals(sharedPokemonString, retrievedString)
    }

}
