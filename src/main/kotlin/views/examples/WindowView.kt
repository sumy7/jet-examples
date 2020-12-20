package views.examples

import androidx.compose.desktop.AppManager
import androidx.compose.desktop.AppWindowAmbient
import androidx.compose.desktop.Window
import androidx.compose.desktop.WindowEvents
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*

@Composable
fun windowView() {
    val dialogState = remember { mutableStateOf(false) }
    val appWindowState = remember { mutableStateOf(false) }
    val centerWindowState = remember { mutableStateOf(false) }
    val windowEventState = remember { mutableStateOf(false) }

    Column {
        Button(onClick = {
            dialogState.value = true
        }) {
            Text("Open Dialog")
        }
        Spacer(Modifier.padding(10.dp))
        Button(onClick = {
            appWindowState.value = true
        }) {
            Text("Open APP")
        }
        Spacer(Modifier.padding(10.dp))
        Button(onClick = {
            centerWindowState.value = true
        }) {
            Text("Center Window")
        }
        Spacer(Modifier.padding(10.dp))
        Button(onClick = {
            windowEventState.value = true
        }) {
            Text("Window Event")
        }
    }
    if (dialogState.value) {
        dialog(dialogState)
    }
    if (appWindowState.value) {
        app(appWindowState)
    }
    if (centerWindowState.value) {
        centerWindow(centerWindowState)
    }
    if (windowEventState.value) {
        windowEvent(windowEventState)
    }
}

@Composable
fun dialog(dialogState: MutableState<Boolean>) {
    Dialog(
        onDismissRequest = { dialogState.value = false }
    ) {
        Text("Nothing there :)")
    }
}

@Composable
fun app(appState: MutableState<Boolean>) {
    val count = mutableStateOf(0)
    val windowPos = mutableStateOf(IntOffset.Zero)

    Window(
        title = "MyApp",
        size = IntSize(400, 250),
        location = IntOffset(100, 100),
        centered = false, // true - by default
        menuBar = MenuBar(
            Menu(
                name = "Actions",
                MenuItem(
                    name = "Increment value",
                    onClick = {
                        count.value++
                    },
                    shortcut = KeyStroke(Key.I)
                ),
                MenuItem(
                    name = "Exit",
                    onClick = {
                        AppManager.focusedWindow?.close()
                    },
                    shortcut = KeyStroke(Key.X)
                )
            )
        ),
        undecorated = true, // false - by default
        events = WindowEvents(
            onRelocate = { location ->
                windowPos.value = location
            }
        ),
        onDismissRequest = {
            appState.value = false
        }
    ) {
        val window = AppWindowAmbient.current
        // content
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column {
                Text(text = "Location: ${windowPos.value} Value: ${count.value}")
                Button(
                    onClick = {
                        window?.close()
                    }
                ) {
                    Text(text = "Close app")
                }
            }
        }
    }
}

@Composable
fun centerWindow(centerWindowState: MutableState<Boolean>) {
    Window(centered = true,
        onDismissRequest = {
            centerWindowState.value = false
        }) {
        val window = AppWindowAmbient.current
        // content
        Button(
            onClick = {
                window?.setWindowCentered()
            }
        ) {
            Text(text = "Center the window")
        }
    }
}

@Composable
fun windowEvent(windowEventState: MutableState<Boolean>) {
    val windowSize = mutableStateOf(IntSize.Zero)
    val focused = mutableStateOf(false)

    Window(
        events = WindowEvents(
            onFocusGet = { focused.value = true },
            onFocusLost = { focused.value = false },
            onResize = { size ->
                windowSize.value = size
            }
        ),
        onDismissRequest = {
            windowEventState.value = false
        }
    ) {
        // content
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Size: ${windowSize.value} Focused: ${focused.value}")
        }
    }
}
