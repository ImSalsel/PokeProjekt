package cz.mendelu.pef.pokeprojekt.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ramcosta.composedestinations.DestinationsNavHost
import cz.mendelu.pef.pokeprojekt.ui.screens.NavGraphs
import cz.mendelu.pef.pokeprojekt.ui.screens.pokemonList.ListOfPokemon
import cz.mendelu.pef.pokeprojekt.ui.theme.PokeProjektTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokeProjektTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)

            }
        }
    }
}
