package cz.mendelu.pef.pokeprojekt.ui.screens.pokemonPokedex

import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import cz.mendelu.pef.pokeprojekt.R
import cz.mendelu.pef.pokeprojekt.architecture.BaseViewModel
import cz.mendelu.pef.pokeprojekt.architecture.CommunicationResult
import cz.mendelu.pef.pokeprojekt.communication.pokemon.IPokemonRemoteRepository
import cz.mendelu.pef.pokeprojekt.communication.pokemon.PokemonRemoteRepositoryImpl
import cz.mendelu.pef.pokeprojekt.database.IPokemonRepository
import cz.mendelu.pef.pokeprojekt.database.PokemonRoom
import cz.mendelu.pef.pokeprojekt.model.PokemonSpecies
import cz.mendelu.pef.pokeprojekt.model.UiState
import cz.mendelu.pef.pokeprojekt.ui.screens.map.MapErrors
import cz.mendelu.pef.pokeprojekt.ui.screens.pokemonDetail.PokemonDetailErrors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class PokemonPokedexViewModel @Inject constructor(
    private val pokemonRemoteRepositoryImpl: IPokemonRemoteRepository,
    private val repository: IPokemonRepository

) : BaseViewModel() {


    val pokemonUIState: MutableState<UiState<PokemonRoom, PokemonPokedexErrors>> =
        mutableStateOf(UiState())

    var pokemonSpecies: MutableState<PokemonSpecies?> = mutableStateOf(null)


    fun getPokemonSpeciesDetails(idDatabse: Int,idPokemon: Int) {
        launch {
            val result = withContext(Dispatchers.IO) {
                pokemonRemoteRepositoryImpl.getPokemonSpeciesById(idPokemon)

            }
            when (result) {
                is CommunicationResult.CommunicationError ->
                    pokemonUIState.value = UiState(
                        loading = false,
                        data = null,
                        errors = PokemonPokedexErrors(R.string.no_internet_connection)
                    )

                is CommunicationResult.Error ->
                    pokemonUIState.value = UiState(
                        loading = false,
                        data = null,
                        errors = PokemonPokedexErrors(R.string.failed_to_load_the_list)
                    )

                is CommunicationResult.Exception ->
                    pokemonUIState.value = UiState(
                        loading = false,
                        data = null,
                        errors = PokemonPokedexErrors(R.string.unknown_error)
                    )

                is CommunicationResult.Success -> {
                    pokemonUIState.value = UiState(
                        loading = false,
                        data = repository.getPokemonById(id = idDatabse),
                        errors = null
                    )
                    pokemonSpecies.value = result.data
                }
            }
        }
    }

    fun generateQRCode(content: String): Bitmap {
        val writer = QRCodeWriter()
        val bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 512, 512)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
            }
        }
        return bitmap
    }


}