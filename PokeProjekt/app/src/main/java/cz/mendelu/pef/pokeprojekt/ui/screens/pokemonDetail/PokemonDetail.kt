package cz.mendelu.pef.pokeprojekt.ui.screens.pokemonDetail

import android.graphics.drawable.Icon
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import cz.mendelu.pef.pokeprojekt.R
import cz.mendelu.pef.pokeprojekt.database.PokemonRoom
import cz.mendelu.pef.pokeprojekt.model.MoveDetailApi
import cz.mendelu.pef.pokeprojekt.model.Pokemon
import cz.mendelu.pef.pokeprojekt.model.UiState
import cz.mendelu.pef.pokeprojekt.ui.elements.BaseScreen
import cz.mendelu.pef.pokeprojekt.ui.elements.MoveItem
import cz.mendelu.pef.pokeprojekt.ui.elements.TypeIcons
import cz.mendelu.pef.pokeprojekt.ui.screens.destinations.PokemonPokedexScreenDestination
import cz.mendelu.pef.pokeprojekt.ui.screens.map.MapErrors
import cz.mendelu.pef.pokeprojekt.ui.theme.getTintColor


@Destination
@Composable
fun PokemonDetail(
    id: Long?,
    navigator: DestinationsNavigator
) {


    val viewModel = hiltViewModel<PokemonDetailViewModel>()


    viewModel.pokemonID = id

    val uiState: MutableState<UiState<PokemonRoom, PokemonDetailErrors>> =
        rememberSaveable { mutableStateOf(UiState()) }

    viewModel.pokemonUIState.value.let {
        uiState.value = it
    }

    if (uiState.value.loading) {
        viewModel.initPokemon()
    }

    BaseScreen(
        topBarText = stringResource(R.string.pokemon_detail),
        drawFullScreenContent = true,
        showLoading = uiState.value.loading,
        actions = {
            IconButton(onClick = {
                navigator.navigate(
                    PokemonPokedexScreenDestination(
                        idDatabase = uiState.value.data?.id!!.toInt(),
                        idPokemon = uiState.value.data?.pokemonId!!
                    )
                )
            }) {
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = "info",
                    tint = getTintColor()
                )
            }
        }
    ) {
        PokemonBattleScreenContent(
            paddingValues = it,
            uiState = uiState,
            viewModel = viewModel

        )
    }
}

@Composable
fun PokemonBattleScreenContent(
    paddingValues: PaddingValues,
    uiState: MutableState<UiState<PokemonRoom, PokemonDetailErrors>>,
    viewModel: PokemonDetailViewModel


) {

    val isFavorite by viewModel.isFavoriteLiveData.observeAsState()
    val move1 by viewModel.move1LiveData.observeAsState()
    val move2 by viewModel.move2LiveData.observeAsState()
    val move3 by viewModel.move3LiveData.observeAsState()
    val move4 by viewModel.move4LiveData.observeAsState()



    Column(
        modifier = Modifier
            .padding(paddingValues)
            .background(Color(0xFF51A3D0))
            .fillMaxHeight()
            .fillMaxWidth()
    ) {

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)

        ) {
            AsyncImage(
                model = uiState.value.data?.frontDefaultSprite,
                contentDescription = null,
                modifier = Modifier
                    .width(200.dp)
                    .fillMaxHeight()
                    .testTag("image"),
                contentScale = ContentScale.FillBounds
            )

        }

        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            uiState.value.data?.name?.let {
                Text(
                    text = it, modifier = Modifier
                        .padding(),
                    fontSize = 25.sp
                )
            }
            Text(
                text = " Lv.${uiState.value.data?.level.toString()}", modifier = Modifier
                    .padding(),
                fontSize = 25.sp
            )
            IconButton(onClick = { viewModel.setPokemonAsFavorite(uiState.value.data?.id!!) }) {
                if (isFavorite == true) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "Start"
                    )
                } else {
                    Icon(
                        imageVector = Icons.Filled.FavoriteBorder,
                        contentDescription = "Start"
                    )
                }

            }
        }

        uiState.value.data?.types?.let { TypeIcons(typesString = it) }


        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
           // Text(text = stringResource(R.string.moves))
        }
        uiState.value.data?.moves?.let { movesString ->
            val movesList = movesString.split(",").map { it.trim() }
            Column(modifier = Modifier.testTag("moves")) {
                move1?.let { MoveItem(it) }
                move2?.let { MoveItem(it) }
                move3?.let { MoveItem(it) }
                move4?.let { MoveItem(it) }
            }
        }

    }
}

