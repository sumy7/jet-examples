package views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.ExperimentalKeyInput
import androidx.compose.ui.unit.dp
import model.ExampleNode
import views.examples.*

@ExperimentalFoundationApi
@ExperimentalKeyInput
val exampleList = listOf(
    ExampleNode("hello-view", "Hello World") { helloView() },
    ExampleNode("hello-world-2", "Hello World 2") { helloWorld2View() },
    ExampleNode("jframe-properties", "JFrameProperties") { jFramePropertiesView() },
    ExampleNode("dropdown", "Dropdown") { dropdownView() },
    ExampleNode("desktop-api", "Desktop API") { desktopAPIView() },
    ExampleNode("mvvm", "MVVM") { mvvmView() },
    ExampleNode("calculator-layout", "Calculator Layout") { calculatorView() },
    ExampleNode("animation", "Animation") { animationView() },
    ExampleNode("keyboard", "Keyboard") { keyboardView() },
    ExampleNode("mouse-event", "Mouse Event") { mouseEventView() },
    ExampleNode("window", "Window") { windowView() },
    ExampleNode("scrollbar", "Scrollbar") { scrollbarView() },
)


@OptIn(ExperimentalKeyInput::class, ExperimentalFoundationApi::class)
@Composable
fun mainView() {
    val currentExample: MutableState<ExampleNode?> = remember { mutableStateOf(null) }
    Row(Modifier.fillMaxSize()) {
        Box(modifier = Modifier.width(200.dp), contentAlignment = Alignment.Center) {
            exampleList(currentExample)
        }
        Box(modifier = Modifier.padding(10.dp)) {
            exampleView(currentExample.value)
        }
    }
}

@OptIn(ExperimentalKeyInput::class, ExperimentalFoundationApi::class)
@Composable
fun exampleList(currentExample: MutableState<ExampleNode?>) {
    val scroll = rememberScrollState(0f)
    Column {
        Scaffold(topBar = {
            TopAppBar(title = { Text(text = "JetExample") })
        },
            bodyContent = {
                Column {
                    ScrollableColumn(scrollState = scroll) {
                        exampleList.let {
                            for (example in it) {
                                Box(
                                    modifier = Modifier.clickable {
                                        currentExample.value = example
                                    }, contentAlignment = Alignment.CenterStart
                                ) {
                                    Card(
                                        modifier = Modifier.fillMaxWidth(),
                                        backgroundColor = if (example == currentExample.value) {
                                            Color.Gray
                                        } else {
                                            Color.White
                                        }
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(10.dp).fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Column {
                                                Text("${example.title}")
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            })
    }
}

@Composable
fun exampleView(currentExampleValue: ExampleNode?) {
    if (currentExampleValue == null) {
        Text("Select an example")
    } else {
        currentExampleValue.entry()
    }
}
