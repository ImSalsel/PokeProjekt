package cz.mendelu.pef.pokeprojekt

import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import cz.mendelu.pef.pokeprojekt.ui.activities.MainActivity
import cz.mendelu.pef.pokeprojekt.ui.screens.NavGraphs
import cz.mendelu.pef.pokeprojekt.ui.screens.destinations.PokemonDetailDestination
import cz.mendelu.pef.pokeprojekt.ui.screens.destinations.PokemonPokedexScreenDestination
import cz.mendelu.pef.pokeprojekt.ui.theme.PokeProjektTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters

@ExperimentalCoroutinesApi
@HiltAndroidTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class UITestDetailScreenTest {
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
    fun are_text_and_moves_displayed() {
        launchDetailScreenWithNavigation(1)
        Thread.sleep(2000)
        composeRule.onNodeWithTag("moves").assertIsDisplayed()
        composeRule.onNodeWithTag("image").assertIsDisplayed()
        Thread.sleep(1000)
    }


    @OptIn(ExperimentalAnimationApi::class)
    private fun launchDetailScreenWithNavigation(arg1: Long) {
        composeRule.activity.setContent {
            PokeProjektTheme {
                navController = rememberNavController()
                DestinationsNavHost(
                    navController = navController,
                    navGraph = NavGraphs.root
                )

                LaunchedEffect(Unit) {
                    navController.navigate(
                        route = PokemonDetailDestination(arg1).route
                    )
                }
            }
        }
    }

}