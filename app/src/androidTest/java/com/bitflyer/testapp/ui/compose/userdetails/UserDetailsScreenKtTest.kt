package com.bitflyer.testapp.ui.compose.userdetails

import android.content.res.Resources
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.platform.app.InstrumentationRegistry
import com.bitflyer.testapp.R
import com.bitflyer.testapp.ui.compose.theme.BitflyerTheme
import com.bitflyer.testapp.ui.userdetails.state.UserDetailsScreenState
import com.biyflyer.testapp.userDetailsModelMock
import org.junit.Rule
import org.junit.Test

internal class UserDetailsScreenKtTest {
    @get: Rule
    val composeRule = createComposeRule()
    val res: Resources =
        InstrumentationRegistry.getInstrumentation().targetContext.resources
    val mock = userDetailsModelMock

    private fun getString(id: Int) = res.getString(id)

    @Test
    fun allProfileDetailsAreShownWhenStateIsLoaded() {
        composeRule.setContent {
            BitflyerTheme {
                UserDetailsScreen(
                    onReloadClick = { }, state = UserDetailsScreenState.Loaded(
                        mock
                    )
                )
            }
        }
        //correct labels are shown
        composeRule.onNodeWithText(getString(R.string.blog_label))
            .assertIsDisplayed()
        composeRule.onNodeWithText(getString(R.string.company_label))
            .assertIsDisplayed()
        composeRule.onNodeWithText(getString(R.string.location_label))
            .assertIsDisplayed()
        composeRule.onNodeWithText(getString(R.string.twitter_label))
            .assertIsDisplayed()
        composeRule.onNodeWithText(getString(R.string.total_repos_label))
            .assertIsDisplayed()

        //correct values are shown
        composeRule.onNodeWithTag(mock.avatarUrl).assertIsDisplayed()
        composeRule.onNodeWithText(mock.name!!).assertIsDisplayed()
        composeRule.onNodeWithText(mock.twitter!!).assertIsDisplayed()
        composeRule.onNodeWithText(mock.blogUrl!!).assertIsDisplayed()
        composeRule.onNodeWithText(
            "${getString(R.string.followers_label)} ${mock.followers}"
        ).assertIsDisplayed()
        composeRule.onNodeWithText(
            "${getString(R.string.following_label)} ${mock.following}"
        ).assertIsDisplayed()
        composeRule.onNodeWithText(mock.repos).assertIsDisplayed()
        composeRule.onNodeWithText(mock.company!!).assertIsDisplayed()
        composeRule.onNodeWithText(mock.location!!).assertIsDisplayed()
    }
}