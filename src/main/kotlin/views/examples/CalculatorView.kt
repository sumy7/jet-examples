package views.examples

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.fontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.platform.font
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val CALCULATOR_PADDING = 4.dp

@Composable
fun calculatorView() {
    val mainOutput = remember { mutableStateOf(TextFieldValue("0")) }
    Column(Modifier.fillMaxHeight()) {
        displayPanel(
            Modifier.weight(1f),
            mainOutput
        )
        keyboard(
            Modifier.weight(4f),
            mainOutput
        )
    }
}

@Composable
fun displayPanel(
    modifier: Modifier,
    mainOutput: MutableState<TextFieldValue>
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface)
            .padding(CALCULATOR_PADDING)
            .background(Color.White)
            .border(color = Color.Gray, width = 1.dp)
            .padding(start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End
    ) {
        Text(
            text = mainOutput.value.text,
            style = TextStyle(
                fontSize = 48.sp,
            ),
            overflow = TextOverflow.Ellipsis,
            softWrap = false,
            maxLines = 1,
        )
    }
}

val KEY_BORDER_WIDTH = 1.dp
val KEY_BORDER_COLOR = Color.Gray
val KEY_ACTIVE_BACKGROUND = Color.White

val KeyboardLayout = listOf(
    listOf("7", "4", "1", "Clear"),
    listOf("8", "5", "2", "0"),
    listOf("9", "6", "3", "."),
    listOf("÷", "x", "-", "+"),
    listOf("<-", "=")
)

@Composable
fun keyboard(
    modifier: Modifier,
    mainOutput: MutableState<TextFieldValue>
) {
    Surface(modifier) {
        keyboardKeys(mainOutput)
    }
}

@Composable
fun keyboardKeys(mainOutput: MutableState<TextFieldValue>) {
    Row(modifier = Modifier.fillMaxSize()) {
        KeyboardLayout.forEach { keyColumn ->
            Column(modifier = Modifier.weight(1f)) {
                keyColumn.forEach { key ->
                    keyboardKey(Modifier.weight(1f), key, mainOutput)
                }
            }
        }
    }
}

@Composable
fun keyboardKey(modifier: Modifier, key: String?, mainOutput: MutableState<TextFieldValue>) {
    if (key == null) {
        return emptyKeyView(modifier)
    }
    keyView(modifier = modifier.padding(1.dp), onClick = {
        println("Press ${key}")
    }) {
        Text(
            text = key,
            style = TextStyle(
                fontSize = 29.sp
            )
        )
    }
}

@Composable
fun keyView(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    children: @Composable ColumnScope.() -> Unit
) {
    val active = remember { mutableStateOf(false) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxWidth()
            .padding(CALCULATOR_PADDING)
            .clickable { onClick() }
            .background(color = if (active.value) KEY_ACTIVE_BACKGROUND else MaterialTheme.colors.background)
            .border(width = KEY_BORDER_WIDTH, color = KEY_BORDER_COLOR)
            .pointerMoveFilter(
                onEnter = {
                    active.value = true
                    false
                },
                onExit = {
                    active.value = false
                    false
                }
            )
    ) {
        children()
    }
}

@Composable
fun emptyKeyView(modifier: Modifier) = Box(
    modifier = modifier.fillMaxWidth()
        .background(MaterialTheme.colors.background)
        .border(width = KEY_BORDER_WIDTH, color = KEY_BORDER_COLOR)
)

