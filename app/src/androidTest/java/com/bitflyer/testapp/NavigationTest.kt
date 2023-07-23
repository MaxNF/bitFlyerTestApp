package com.bitflyer.testapp

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.bitflyer.testapp.ui.BitflyerApp
import com.bitflyer.testapp.ui.compose.navigation.Destination
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class NavigationTest {

    @get:Rule(order = 1)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeRule = createAndroidComposeRule<TestHiltActivity>()

    lateinit var navController: TestNavHostController

    @Before
    fun setup() {
        hiltRule.inject()
        composeRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            BitflyerApp(navController)
        }
    }

    @Test
    fun testNavigation() {
        val oldRoute = navController.currentBackStackEntry?.destination?.route
        assertEquals(oldRoute, Destination.UserList.route)
        composeRule.onNodeWithText("login").performClick()
        val newRoute = navController.currentBackStackEntry?.destination?.route
        assertEquals(newRoute, Destination.UserDetails.route)
    }
}