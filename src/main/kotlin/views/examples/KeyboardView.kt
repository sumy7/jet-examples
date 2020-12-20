package views.examples

import androidx.compose.desktop.AppWindow
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.ExperimentalKeyInput
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.plus
import androidx.compose.ui.input.key.shortcuts
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

@ExperimentalKeyInput
@Composable
fun keyboardView() {
    var consumedText by remember { mutableStateOf(0) }
    var text by remember { mutableStateOf("") }

    Column(Modifier.fillMaxSize(), Arrangement.spacedBy(5.dp)) {
        Text("Consumed text: $consumedText")
        TextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier.shortcuts {
                on(Key.CtrlLeft + Key.Minus) {
                    consumedText -= text.length
                    text = ""
                }
                on(Key.CtrlLeft + Key.Backslash) {
                    consumedText += text.length
                    text = ""
                }
            }
        )
        Text("text --> ${text}")
        Text("input text and try [Ctrl + -] or [Ctrl + \\] ")
        Spacer(Modifier.height(20.dp))
        Button(
            modifier = Modifier.padding(4.dp),
            onClick = {
                AppWindow(size = IntSize(200, 200)).also {
                    it.keyboard.setShortcut(Key.Escape) {
                        it.close()
                    }
                }.show {
                    Text("I'm popup!\n[Esc] to close!")
                }
            }
        ) {
            Text("Open popup")
        }
    }
}
