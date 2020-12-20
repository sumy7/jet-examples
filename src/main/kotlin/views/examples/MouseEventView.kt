package views.examples

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun mouseEventView() {
    Column {
        var count by remember { mutableStateOf(0) }
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
            var text by remember { mutableStateOf("Click me!") }
            Text(
                text = text,
                fontSize = 50.sp,
                modifier = Modifier.clickable(
                    onClick = {
                        text = "Click! ${count++}"
                    },
                    onDoubleClick = {
                        text = "Double click! ${count++}"
                    },
                    onLongClick = {
                        text = "Long click! ${count++}"
                    }
                ).align(Alignment.Center)
            )
        }

        var color by remember { mutableStateOf(Color(255, 255, 255)) }
        Box(
            modifier = Modifier.wrapContentSize(Alignment.Center)
                .fillMaxWidth()
                .height(256.dp)
                .background(color = color)
                .pointerMoveFilter(onMove = {
                    color = Color(it.x.toInt() % 256, it.y.toInt() % 256, 0)
                    false
                })
        ) {
            Text("Mouse Move", fontSize = 50.sp)
        }

        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            repeat(10) { index ->
                var active by remember { mutableStateOf(false) }
                Text(
                    modifier = Modifier.fillMaxWidth()
                        .background(color = if (active) Color.Green else Color.White)
                        .pointerMoveFilter(onEnter = {
                            active = true
                            false
                        },
                            onExit = {
                                active = false
                                false
                            }),
                    fontSize = 30.sp,
                    fontStyle = if (active) FontStyle.Italic else FontStyle.Normal,
                    text = "Item $index"
                )
            }
        }
    }
}
