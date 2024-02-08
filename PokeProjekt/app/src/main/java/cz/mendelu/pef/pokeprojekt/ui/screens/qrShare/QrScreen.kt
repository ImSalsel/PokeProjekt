package cz.mendelu.pef.pokeprojekt.ui.screens.qrShare

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import cz.mendelu.pef.pokeprojekt.model.Pokemon
import cz.mendelu.pef.pokeprojekt.model.UiState
import cz.mendelu.pef.pokeprojekt.ui.elements.BaseScreen
import cz.mendelu.pef.pokeprojekt.ui.elements.PokemonItemStart
import cz.mendelu.pef.pokeprojekt.ui.screens.start.PokemonStartErrors
import cz.mendelu.pef.pokeprojekt.ui.screens.start.PokemonStartViewModel
import cz.mendelu.pef.pokeprojekt.ui.theme.getTintColor

@Destination(route = "QrScreen")
@Composable
fun QrScreen(
    navigator: DestinationsNavigator
) {


    BaseScreen(
        topBarText = null,
        drawFullScreenContent = true,
        showLoading = false,
    ) {
        QrScreenContent(
            paddingValues = it,
        )
    }


}

@Composable
fun QrScreenContent(
    paddingValues: PaddingValues
) {


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color(0xFF51A3D0))
    ) {

        val context = LocalContext.current

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "QR Scan", modifier = Modifier, fontSize = 24.sp)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                context.startActivity(
                    Intent(
                        context,
                        CameraXLivePreviewActivity::class.java
                    )
                )

            }) {
                Text(text = "Start Scanning")
            }

        }
    }
}