package views.examples

import androidx.compose.desktop.AppManager
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*

@Composable
fun jFramePropertiesView() {
    val isAlwaysOnTop = AppManager.focusedWindow?.window?.isAlwaysOnTop ?: false
    var alwaysOnTop by remember { mutableStateOf(isAlwaysOnTop) }
    onCommit(alwaysOnTop) {
        val jFrame = AppManager.focusedWindow?.window
        jFrame?.let {
            // Use JFrame-specific methods
            it.isAlwaysOnTop = alwaysOnTop
        }
    }

    Column {
        Button(onClick = { alwaysOnTop = !alwaysOnTop }) {
            Text("Toggle always on top = ${alwaysOnTop}")
        }
    }

}
