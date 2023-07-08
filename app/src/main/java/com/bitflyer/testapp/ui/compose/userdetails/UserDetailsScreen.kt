package com.bitflyer.testapp.ui.compose.userdetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun UserDetailsScreen(
    onReloadClick: (String) -> Unit,
    onBackClick: () -> Unit
) {
    Surface {
        Box(modifier = Modifier.fillMaxWidth()) {
            Button(onClick = onBackClick, modifier = Modifier.size(100.dp)) {
                Text("BACK")
            }
        }
    }
}

@Preview
@Composable
fun UserDetailsScreenPreview() {
    UserDetailsScreen(onReloadClick = {}) {

    }
}