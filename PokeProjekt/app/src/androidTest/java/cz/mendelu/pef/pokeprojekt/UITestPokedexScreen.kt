package cz.mendelu.pef.pokeprojekt

import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import cz.mendelu.pef.pokeprojekt.ui.activities.MainActivity
import cz.mendelu.pef.pokeprojekt.ui.screens.NavGraphs
import cz.mendelu.pef.pokeprojekt.ui.screens.destinations.PokemonPokedexScreenDestination
import cz.mendelu.pef.pokeprojekt.ui.screens.destinations.PokemonStartScreenDestination
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
class UITestPokedexScreen {
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
    fun is_QRCode_displayed() {
        launchPokedexScreenWithNavigation(1, 0)
        Thread.sleep(2000)
        composeRule.onNodeWithTag("QRCode").assertIsDisplayed()
        Thread.sleep(1000)
    }

    @Test
    fun is_text_displayed() {
        launchPokedexScreenWithNavigation(1, 0)
        Thread.sleep(2000)
        composeRule.onNodeWithTag("text").assertIsDisplayed()
        Thread.sleep(1000)
    }

    @Test
    fun is_image_displayed() {
        launchPokedexScreenWithNavigation(1, 0)
        Thread.sleep(2000)
        composeRule.onNodeWithTag("image").assertIsDisplayed()
        Thread.sleep(1000)
    }

    @OptIn(ExperimentalAnimationApi::class)
    private fun launchPokedexScreenWithNavigation(arg1: Int, arg2: Int) {
        composeRule.activity.setContent {
            PokeProjektTheme {
                navController = rememberNavController()
                DestinationsNavHost(
                    navController = navController,
                    navGraph = NavGraphs.root
                )

                LaunchedEffect(Unit) {
                    navController.navigate(
                        route = PokemonPokedexScreenDestination(arg1, arg2).route
                    )
                }
            }
        }
    }

}
