package cz.mendelu.pef.pokeprojekt.ui.screens.pokemonList

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import cz.mendelu.pef.pokeprojekt.R
import cz.mendelu.pef.pokeprojekt.model.UiState
import cz.mendelu.pef.pokeprojekt.ui.elements.BaseScreen
import cz.mendelu.pef.pokeprojekt.ui.elements.PlaceholderScreenContent
import cz.mendelu.pef.pokeprojekt.ui.elements.PokemonCard
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.Color
import cz.mendelu.pef.pokeprojekt.ui.screens.qrShare.CameraXLivePreviewActivity
import cz.mendelu.pef.pokeprojekt.ui.theme.getTintColor
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import cz.mendelu.pef.pokeprojekt.ui.screens.destinations.MapScreenDestination
import cz.mendelu.pef.pokeprojekt.ui.screens.destinations.QrScreenDestination


@Destination(route = "list_of_pokemons")
@Composable
fun ListOfPokemon(
    navigator: DestinationsNavigator
) {

    val context = LocalContext.current
    val viewModel = hiltViewModel<ListOfPokemonViewModel>()

    val uiState: MutableState<UiState<ListOfPokemonData, ListOfPokemonErrors>> =
        rememberSaveable {
            mutableStateOf(UiState())
        }

    viewModel.pokemonUIState.value.let {
        uiState.value = it

    }



    BaseScreen(
        topBarText = stringResource(R.string.list_of_pokemons),
        drawFullScreenContent = true,
        showLoading = false,
        actions = {
            IconButton(onClick = {
                uiState.value = UiState()
                //navigator.navigate(QrScreenDestination)
                context.startActivity(
                    Intent(
                        context,
                        CameraXLivePreviewActivity::class.java
                    )
                )
            }) {
                Icon(
                    imageVector = Icons.Filled.QrCode2,
                    contentDescription = "QrCode",
                    tint = getTintColor()
                )
            }
        },
        placeholderScreenContent = if (uiState.value.errors != null) {
            PlaceholderScreenContent(
                null,
                stringResource(id = uiState.value.errors!!.communicationError)
            )
        } else
            null,
        floatingActionButton = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                IconButton(
                    onClick = { navigator.popBackStack() },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp, start = 32.dp)
                        .testTag("ButtonToMap")
                        .size(64.dp),
                    content = {
                        Image(
                            painter = painterResource(id = R.drawable.pokemon),
                            contentDescription = "Button Image",
                            modifier = Modifier.background(Color.Transparent)
                        )
                    }
                )
            }

        }
    ) {
        ListOfPokemonScreenContent(
            paddingValues = it,
            pokemonData = uiState.value.data,
            navigator = navigator
        )
    }


}

@Composable
fun ListOfPokemonScreenContent(
    paddingValues: PaddingValues,
    pokemonData: ListOfPokemonData?,
    navigator: DestinationsNavigator
) {


    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .background(Color(0xFF51A3D0))
            .fillMaxHeight()
            .testTag("ListOfPokemon")
    )
    {


        pokemonData?.pokemons?.forEach {
            if (it.name != null)
                item {
                    PokemonCard(it, navigator)
                    //
                }
        }
    }


    IconButton(
        onClick = { navigator.navigate(MapScreenDestination) },
        modifier = Modifier
            .padding(bottom = 32.dp)
            .size(64.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.pokemon),
            contentDescription = "Custom Icon",
        )
    }


}

