package com.github.uragiristereo.safer.compose.sample.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun FeedScreen(
    onProfileClick: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize(),
    ) {
        Text(text = "Feed Screen")

        Button(
            onClick = onProfileClick,
            content = {
                Text(text = "Go to Profile Screen")
            },
        )

        Button(
            onClick = onSettingsClick,
            content = {
                Text(text = "Go to Settings Screen")
            },
        )
    }
}
