package cz.mendelu.pef.pokeprojekt.ui.screens.map

import android.os.CountDownTimer
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import cz.mendelu.pef.pokeprojekt.R
import cz.mendelu.pef.pokeprojekt.architecture.BaseViewModel
import cz.mendelu.pef.pokeprojekt.architecture.CommunicationResult
import cz.mendelu.pef.pokeprojekt.communication.pokemon.PokemonRemoteRepositoryImpl
import cz.mendelu.pef.pokeprojekt.database.IPokemonRepository
import cz.mendelu.pef.pokeprojekt.database.PokemonRoom
import cz.mendelu.pef.pokeprojekt.database.PokemonsDatabase
import cz.mendelu.pef.pokeprojekt.datastore.IDataStoreRepository
import cz.mendelu.pef.pokeprojekt.model.Pokemon
import cz.mendelu.pef.pokeprojekt.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.random.Random


@HiltViewModel
class MapViewModel @Inject constructor(
    private val pokemonRemoteRepositoryImpl: PokemonRemoteRepositoryImpl,
    private val pokemonDataStore: IDataStoreRepository,
    private val repository: IPokemonRepository
) : BaseViewModel() {


    val pokemonUIState: MutableState<UiState<Pokemon, MapErrors>> = mutableStateOf(UiState())

    var wildPokemonList by mutableStateOf(mutableListOf<Pokemon>())
        private set

    private var timer: CountDownTimer? = null


    val hasFavoritePokemon = MutableLiveData<Boolean>()

    init {
        checkForSharedPokemon()
        startLoadingPokemon()
        if (wildPokemonList.isEmpty()) {
            findPokemons()
        }
    }

    fun checkForFavoritePokemon() {
        viewModelScope.launch {
            val favoritePokemonId = pokemonDataStore.getFavoritePokemonId()
            Log.d(
                "StartViewModel",
                "checkForFavoritePokemonDataStore:${pokemonDataStore.getFavoritePokemonId()}"
            )
            hasFavoritePokemon.value = favoritePokemonId != null && favoritePokemonId > 0
        }
    }

    fun findPokemons() {
        val rnds = (1..494).random()

        launch {
            val result = withContext(Dispatchers.IO) {
                pokemonRemoteRepositoryImpl.getPokemonById(rnds.toLong())
            }
            when (result) {
                is CommunicationResult.CommunicationError ->
                    pokemonUIState.value = UiState(
                        loading = false,
                        data = null,
                        errors = MapErrors(R.string.no_internet_connection)
                    )

                is CommunicationResult.Error ->
                    pokemonUIState.value = UiState(
                        loading = false,
                        data = null,
                        errors = MapErrors(R.string.failed_to_load_the_list)
                    )

                is CommunicationResult.Exception ->
                    pokemonUIState.value = UiState(
                        loading = false,
                        data = null,
                        errors = MapErrors(R.string.unknown_error)
                    )

                is CommunicationResult.Success -> {
                    pokemonUIState.value = UiState(
                        loading = false,
                        data = result.data,
                        errors = null
                    )
                    wildPokemonList += result.data

                    Log.d("MapViewModel", "API DATA findPokemon ${result.data}")
                }
            }
        }


    }

    fun savePokemonToDataStore(pokemon: Pokemon) {
        viewModelScope.launch {
            pokemonDataStore.setPokemonToCatch(convertPokemon(pokemon))
        }
    }


    fun startLoadingPokemon() {
        timer?.cancel()
        timer = object :
            CountDownTimer(Long.MAX_VALUE, 60000) {
            override fun onTick(millisUntilFinished: Long) {
                if (wildPokemonList.size < 5) {
                    findPokemons()
                }

            }

            override fun onFinish() {
            }
        }
        timer?.start()
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

    fun checkForSharedPokemon() {
        viewModelScope.launch {
            val sharedPokemon = pokemonDataStore.getSharedPokemon()

            if (!sharedPokemon.isNullOrBlank()) {
                Log.d("Share", "$sharedPokemon")

                val pokemonData = sharedPokemon.split(";")

                if (pokemonData.size >= 9) {
                    val pokemonRoom = PokemonRoom(
                        pokemonId = pokemonData[0].toIntOrNull(),
                        name = pokemonData[1],
                        height = pokemonData[2].toIntOrNull(),
                        weight = pokemonData[3].toIntOrNull(),
                        moves = pokemonData[4],
                        frontDefaultSprite = pokemonData[5],
                        types = pokemonData[6],
                        level = pokemonData[7].toIntOrNull() ?: 0,
                        xp = pokemonData[8].toFloatOrNull() ?: 0f,
                        hp = pokemonData[7].toIntOrNull()?.times(3) ?: 15
                    )
                    repository.insert(pokemonRoom)
                    Log.d("Share", "$pokemonRoom")
                } else {
                    Log.e("Share", "Invalid QR code data")
                }
            }
        }
    }


    fun generateRandomLocationNearby(originalLat: Double, originalLng: Double): LatLng {
        val radius: Double = 0.004800
        val randomLatOffset = Random.nextDouble(-radius, radius)
        val randomLngOffset = Random.nextDouble(-radius, radius)

        val newLat = originalLat + randomLatOffset
        val newLng = originalLng + randomLngOffset

        return LatLng(newLat, newLng)
    }

    fun convertPokemon(pokemon: Pokemon): PokemonRoom {
        val moveIds = pokemon.moves.shuffled().take(4).mapNotNull { move ->
            move.move.url.split("/").dropLast(1).lastOrNull()
        }.joinToString(",")

        val pokemonRoom = PokemonRoom(
            pokemonId = pokemon.id,
            name = pokemon.name,
            height = pokemon.height,
            weight = pokemon.weight,
            moves = moveIds,
            frontDefaultSprite = pokemon.sprites.frontDefault,
            types = pokemon.types.joinToString(", ") { it.type.name },
            hp = 0,
            level = 0,
            xp = 0f
        )
        return pokemonRoom

    }
}