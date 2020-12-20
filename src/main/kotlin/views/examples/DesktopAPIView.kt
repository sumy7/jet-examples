package views.examples

import androidx.compose.desktop.AppWindowAmbient
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize

@Composable
fun desktopAPIView() {
    val current = AppWindowAmbient.current
    val currentSize = if (current == null) IntSize.Zero else IntSize(current.width, current.height)
    val currentLocation = if (current == null) IntOffset.Zero else IntOffset(current.x, current.y)
    var windowSize by remember { mutableStateOf(currentSize) }
    var windowLocation by remember { mutableStateOf(currentLocation) }
    AppWindowAmbient.current?.apply {
        events.onResize = { windowSize = it }
        events.onRelocate = { windowLocation = it }
    }
    Text(text = "Location: ${windowLocation}\nSize: ${windowSize}")
}
