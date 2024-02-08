package cz.mendelu.pef.pokeprojekt.ui.screens.start

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cz.mendelu.pef.pokeprojekt.R
import cz.mendelu.pef.pokeprojekt.architecture.BaseViewModel
import cz.mendelu.pef.pokeprojekt.architecture.CommunicationResult
import cz.mendelu.pef.pokeprojekt.communication.pokemon.IPokemonRemoteRepository
import cz.mendelu.pef.pokeprojekt.communication.pokemon.PokemonRemoteRepositoryImpl
import cz.mendelu.pef.pokeprojekt.database.IPokemonRepository
import cz.mendelu.pef.pokeprojekt.database.PokemonRoom
import cz.mendelu.pef.pokeprojekt.datastore.IDataStoreRepository
import cz.mendelu.pef.pokeprojekt.model.Pokemon
import cz.mendelu.pef.pokeprojekt.model.UiState
import cz.mendelu.pef.pokeprojekt.ui.screens.pokemonList.ListOfPokemonData
import cz.mendelu.pef.pokeprojekt.ui.screens.pokemonList.ListOfPokemonErrors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject


@HiltViewModel
class PokemonStartViewModel @Inject constructor(
    private val pokemonRemoteRepositoryImpl: IPokemonRemoteRepository,
    private val pokemonDataStore: IDataStoreRepository,
    private val repository: IPokemonRepository

) : BaseViewModel() {

    var firstPokemon: Pokemon? = null
    var secondPokemon: Pokemon? = null
    var thirdPokemon: Pokemon? = null


    init {
        getStarter(1)
        getStarter(4)
        getStarter(7)
    }

    val pokemonUIState: MutableState<UiState<List<Pokemon>, PokemonStartErrors>> =
        mutableStateOf(UiState())

    fun getStarter(id: Long) {
        launch {
            val result = withContext(Dispatchers.IO) {
                pokemonRemoteRepositoryImpl.getPokemonById(id)
            }
            when (result) {
                is CommunicationResult.CommunicationError ->
                    pokemonUIState.value = UiState(
                        loading = false,
                        data = null,
                        errors = PokemonStartErrors(R.string.no_internet_connection)
                    )

                is CommunicationResult.Error ->
                    pokemonUIState.value = UiState(
                        loading = false,
                        data = null,
                        errors = PokemonStartErrors(R.string.failed_to_load_the_list)
                    )

                is CommunicationResult.Exception ->
                    pokemonUIState.value = UiState(
                        loading = false,
                        data = null,
                        errors = PokemonStartErrors(R.string.unknown_error)
                    )

                is CommunicationResult.Success -> {
                    when (id) {
                        1L -> firstPokemon = result.data
                        4L -> secondPokemon = result.data
                        7L -> thirdPokemon = result.data
                    }
                    updateUIState()
                }
            }
        }


    }

    fun updateUIState() {
        if (firstPokemon != null && secondPokemon != null && thirdPokemon != null) {
            pokemonUIState.value = UiState(
                loading = false,
                data = listOfNotNull(firstPokemon, secondPokemon, thirdPokemon),
                errors = null
            )
        }
    }

    fun savePokemon(pokemon: Pokemon) {
        val moveIds = pokemon.moves.shuffled().take(4).mapNotNull { move ->
            move.move.url.split("/").dropLast(1).lastOrNull()
        }.joinToString(",")

        val pokemonRoom = PokemonRoom(
            id = 1,
            pokemonId = pokemon.id,
            name = pokemon.name,
            height = pokemon.height,
            weight = pokemon.weight,
            moves = moveIds,
            frontDefaultSprite = pokemon.sprites.frontDefault,
            types = pokemon.types.joinToString(", ") { it.type.name },
            hp = 15,
            level = 5,
            xp = 0f

        )
        launch {
            try {
                val existingPokemon = repository.getPokemonById(pokemonRoom.id?.toInt()!!)
                if (existingPokemon == null) {
                    repository.insert(pokemonRoom)
                }
            }
            catch (e: Exception) {
                Log.e("DatabaseError", "Error inserting or updating Pokemon: ${e.message}")
            }

            pokemonDataStore.setFavoritePokemonId(1)
            Log.d("StartViewModel","${pokemonDataStore.getFavoritePokemonId()}")

        }

    }


}