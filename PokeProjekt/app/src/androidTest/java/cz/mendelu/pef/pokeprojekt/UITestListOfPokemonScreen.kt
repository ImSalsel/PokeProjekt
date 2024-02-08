package cz.mendelu.pef.pokeprojekt

import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.FixMethodOrder
import org.junit.runners.MethodSorters
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import cz.mendelu.pef.pokeprojekt.ui.activities.MainActivity
import cz.mendelu.pef.pokeprojekt.ui.screens.NavGraphs
import cz.mendelu.pef.pokeprojekt.ui.screens.destinations.ListOfPokemonDestination
import cz.mendelu.pef.pokeprojekt.ui.theme.PokeProjektTheme
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@HiltAndroidTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class UITestListOfPokemonScreen {

    private lateinit var navController: NavHostController


    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()


    @Before
    fun setUp() {
        hiltRule.inject()

    }

    @Test
    fun test_list_of_pokemons() {
        launchListScreenWithNavigation()
        composeRule.onNodeWithTag("ListOfPokemon").assertIsDisplayed()
        Thread.sleep(5000)
    }

    @Test
    fun test_button_to_map() {
        launchListScreenWithNavigation()
        composeRule.onNodeWithTag("ButtonToMap").assertIsDisplayed()
        Thread.sleep(2000)
        composeRule.onNodeWithTag("ButtonToMap").performClick()
        Thread.sleep(2000)

    }


    @OptIn(ExperimentalAnimationApi::class)
    private fun launchListScreenWithNavigation() {
        composeRule.activity.setContent {
            PokeProjektTheme {
                navController = rememberNavController()
                DestinationsNavHost(
                    navController = navController,
                    navGraph = NavGraphs.root,
                    startRoute = ListOfPokemonDestination
                )
            }
        }
    }
}