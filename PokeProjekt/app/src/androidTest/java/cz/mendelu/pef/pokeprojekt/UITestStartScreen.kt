package cz.mendelu.pef.pokeprojekt

import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag

import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ramcosta.composedestinations.DestinationsNavHost
import cz.mendelu.pef.pokeprojekt.ui.activities.MainActivity
import cz.mendelu.pef.pokeprojekt.ui.screens.NavGraphs
import cz.mendelu.pef.pokeprojekt.ui.screens.destinations.PokemonStartScreenDestination
import cz.mendelu.pef.pokeprojekt.ui.theme.PokeProjektTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class UITestStartScreen {

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
    fun clickOnBulbasaurButton_navigatesToMap() {
        launchStartScreenWithNavigation()
        Thread.sleep(2000)
        composeRule.onNodeWithTag("ditto").assertIsDisplayed()
        composeRule.onNodeWithTag("ditto").performClick()
        Thread.sleep(1000)
    }

    @OptIn(ExperimentalAnimationApi::class)
    private fun launchStartScreenWithNavigation() {
        composeRule.activity.setContent {
            PokeProjektTheme {
                navController = rememberNavController()
                DestinationsNavHost(
                    navController = navController,
                    navGraph = NavGraphs.root,
                    startRoute = PokemonStartScreenDestination
                )
            }
        }
    }

}
