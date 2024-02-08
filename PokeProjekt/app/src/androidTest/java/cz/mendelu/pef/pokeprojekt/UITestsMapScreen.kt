package cz.mendelu.pef.pokeprojekt

import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import cz.mendelu.pef.pokeprojekt.ui.activities.MainActivity
import cz.mendelu.pef.pokeprojekt.ui.screens.NavGraphs
import cz.mendelu.pef.pokeprojekt.ui.screens.destinations.MapScreenDestination
import cz.mendelu.pef.pokeprojekt.ui.screens.map.MapScreen
import cz.mendelu.pef.pokeprojekt.ui.theme.PokeProjektTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters
import org.mockito.Mockito

@ExperimentalCoroutinesApi
@HiltAndroidTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class UITestsMapScreen {

    private lateinit var navController: NavHostController

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @get:Rule(order = 2)
    val grantPermissionRule = GrantPermissionRule.grant(android.Manifest.permission.ACCESS_FINE_LOCATION)

    @get:Rule(order = 2)
    val grantPermissionRule2 = GrantPermissionRule.grant(android.Manifest.permission.ACCESS_COARSE_LOCATION)



    @Before
    fun setup() {
        hiltRule.inject()

    }


    @Test
    fun test_mapIsDisplayed() {
        launchMapScreenWithNavigation()
        composeTestRule.onNodeWithTag("ButtonToList").assertIsDisplayed()

    }


    @OptIn(ExperimentalAnimationApi::class)
    private fun launchMapScreenWithNavigation() {
        composeTestRule.activity.setContent {
            PokeProjektTheme {
                navController = rememberNavController()
                DestinationsNavHost(
                    navController = navController,
                    navGraph = NavGraphs.root,
                    startRoute = MapScreenDestination
                )
            }
        }
    }
}

