package com.bitflyer.testapp.ui.compose.userlist

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.paging.PagingData
import com.bitflyer.testapp.ui.compose.theme.BitflyerTheme
import com.biyflyer.testapp.userBriefEntityMock
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

internal class UserListScreenKtTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun userListIsShownTest() {

        composeRule.setContent {
            BitflyerTheme {
                UserListScreen(list = flowOf(
                    PagingData.from(
                        listOf(
                            userBriefEntityMock.copy(login = "entity_mock_1"),
                            userBriefEntityMock.copy(login = "entity_mock_2"),
                            userBriefEntityMock.copy(login = "entity_mock_3")
                        )
                    )
                ), onUserDetailsClick = {})
            }
        }
        composeRule.onNodeWithText("entity_mock_1").assertExists()
        composeRule.onNodeWithText("entity_mock_2").assertExists()
        composeRule.onNodeWithText("entity_mock_3").assertExists()
    }

}