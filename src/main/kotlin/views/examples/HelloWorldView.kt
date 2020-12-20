package views.examples

import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*

@Composable
fun helloView() {
    var text by remember { mutableStateOf("Hello, World!") }

    MaterialTheme {
        Button(onClick = {
            text = "Hello, Desktop!"
        }) {
            Text(text)
        }
    }
}
