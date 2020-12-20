package model

import androidx.compose.runtime.Composable

data class ExampleNode(
    val code: String,
    val title: String,
    val entry: @Composable () -> Unit
)
