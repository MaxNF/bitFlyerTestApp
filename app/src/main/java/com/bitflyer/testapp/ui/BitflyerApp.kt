package com.bitflyer.testapp.ui

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bitflyer.testapp.ui.compose.navigation.Destination
import com.bitflyer.testapp.ui.compose.navigation.userDetailsScreen
import com.bitflyer.testapp.ui.compose.navigation.userListScreen
import kotlinx.coroutines.delay
import kotlin.random.Random

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BitflyerApp(navController: NavHostController) {
    val currentEntry = navController.currentBackStackEntryAsState()
    Scaffold(
        topBar = {
            TopBar(currentEntry.value, navController)
        }
    ) {
        NavHost(
            modifier = Modifier
                .padding(it)
                .consumeWindowInsets(it),
            navController = navController,
            startDestination = Destination.UserList.route
        ) {
            userListScreen { login ->
                navController.navigate(
                    Destination.UserDetails.createRoute(login)
                )
            }
            userDetailsScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(entry: NavBackStackEntry?, navController: NavHostController) {
    val currentRoute = entry?.destination?.route ?: Destination.UserList.route
    val parentWidth = remember<MutableState<Float?>> { mutableStateOf(null) }
    TopAppBar(
        modifier = Modifier.onGloballyPositioned {
            parentWidth.value =
                it.parentLayoutCoordinates?.size?.toSize()?.width
        },
        navigationIcon = {
            if (currentRoute != Destination.UserList.route) {
                IconButton(onClick = navController::popBackStack) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        },
        title = {
            val title = when (currentRoute) {
                Destination.UserList.route -> "Github Users"
                Destination.UserDetails.route -> navController.currentBackStackEntry?.arguments?.getString(
                    "login"
                ) ?: ""

                else -> ""
            }
            TopBarTitle(title = title, parentWidth.value)
        })
}

@Composable
fun TopBarTitle(title: String, parentWidth: Float?) {
    var jumping: State<Float>? = null
    var colorChange = remember { mutableStateOf(getRandomColor()) }
    if (parentWidth != null) {
        val dp = with(LocalDensity.current) { parentWidth.toDp() }
        val transition = rememberInfiniteTransition()
        jumping = transition.animateFloat(
            initialValue = 0f,
            targetValue = dp.value / 2f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    500,
                    easing = CubicBezierEasing(0f, 0f, 0.2f, 1.0f)
                ),
                repeatMode = RepeatMode.Reverse
            )
        )
        LaunchedEffect(key1 = Unit, block = {
            while (true) {
                delay(1000)
                colorChange.value = getRandomColor()
            }
        })
    }

    Text(
        style = MaterialTheme.typography.titleLarge,
        text = title,
        modifier = Modifier.offset(x = jumping?.value?.dp ?: 0.dp),
        color = colorChange.value
    )
}

private fun getRandomColor(): Color =
    Color(
        red = Random.nextInt(256),
        green = Random.nextInt(256),
        blue = Random.nextInt(256),
        alpha = 255
    )

@Preview()
@Composable
fun TopAppBarPreview() {
    TopBar(entry = null, navController = rememberNavController())
}