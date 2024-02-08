package cz.mendelu.pef.pokeprojekt.ui.screens.pokemonPokedex


import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import cz.mendelu.pef.pokeprojekt.R
import cz.mendelu.pef.pokeprojekt.database.PokemonRoom
import cz.mendelu.pef.pokeprojekt.model.PokemonSpecies
import cz.mendelu.pef.pokeprojekt.model.UiState
import cz.mendelu.pef.pokeprojekt.ui.elements.BaseScreen
import cz.mendelu.pef.pokeprojekt.ui.elements.TypeIcons
import cz.mendelu.pef.pokeprojekt.ui.theme.getTintColor


@Destination
@Composable
fun PokemonPokedexScreen(
    idDatabase: Int,
    idPokemon: Int
) {
    val viewModel = hiltViewModel<PokemonPokedexViewModel>()


    val uiState: MutableState<UiState<PokemonRoom, PokemonPokedexErrors>> =
        rememberSaveable { mutableStateOf(UiState()) }

    viewModel.pokemonUIState.value.let {
        uiState.value = it
    }

    if (uiState.value.loading) {
        viewModel.getPokemonSpeciesDetails(idDatabase, idPokemon)
        Log.d("PokedexScreen", "pokemon pokedex data: ${viewModel.pokemonSpecies.value}")

    }

    BaseScreen(
        topBarText = "pokemon pokedex",
        drawFullScreenContent = true,
        showLoading = uiState.value.loading,
    ) {
        Log.d("PokedexScreen2", "pokemon pokedex data 2: ${viewModel.pokemonSpecies.value}")
        PokemonPokedexScreenContent(
            paddingValues = it,
            uiState = uiState,
            viewModel = viewModel

        )
    }
}


@Composable
fun PokemonPokedexScreenContent(
    paddingValues: PaddingValues,
    uiState: MutableState<UiState<PokemonRoom, PokemonPokedexErrors>>,
    viewModel: PokemonPokedexViewModel

) {
    val qrCodeContent: String =
        "${uiState.value.data?.pokemonId};" +
                "${uiState.value.data?.name};" +
                "${uiState.value.data?.height};" +
                "${uiState.value.data?.weight};" +
                "${uiState.value.data?.moves};" +
                "${uiState.value.data?.frontDefaultSprite};" +
                "${uiState.value.data?.types};" +
                "${uiState.value.data?.level};" +
                "${uiState.value.data?.xp}"
    val qrCodeBitmap = viewModel.generateQRCode(qrCodeContent)

    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .background(Color(0xFF51A3D0))
            .fillMaxHeight()
            .fillMaxWidth()
    ) {

        item {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)

            ) {
                AsyncImage(
                    model = uiState.value.data?.frontDefaultSprite,
                    contentDescription = null,
                    modifier = Modifier
                        .width(250.dp)
                        .fillMaxHeight()
                        .padding(4.dp)
                        .testTag("image"),
                    contentScale = ContentScale.FillBounds

                )

            }
            uiState.value.data?.types?.let { TypeIcons(typesString = it) }

            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 4.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Pokedex ID: ${uiState.value.data?.pokemonId}")
                if (viewModel.pokemonSpecies.value?.isMythical == true) {
                    Text(text = stringResource(R.string.mythical))
                }
                if (viewModel.pokemonSpecies.value?.isLegendary == true) {
                    Text(text = stringResource(R.string.legendary))
                }

            }

            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()

            ) {
                uiState.value.data?.name?.let {
                    Text(
                        text = "$it the ${viewModel.pokemonSpecies.value?.englishGenera}: ",
                        modifier = Modifier
                            .padding(),
                        fontSize = 22.sp
                    )
                }
            }

            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ) {
                viewModel.pokemonSpecies.value?.englishFlavorText?.let {
                    Text(
                        text = it,
                        modifier = Modifier.testTag("text")
                    )
                }
            }


            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 32.dp)
                    .fillMaxWidth()
            ) {
                Image(
                    bitmap = qrCodeBitmap.asImageBitmap(),
                    contentDescription = "",
                    modifier = Modifier.testTag("QRCode")
                )
            }

        }
    }
}