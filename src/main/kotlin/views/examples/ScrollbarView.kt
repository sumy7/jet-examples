package views.examples

import androidx.compose.desktop.DesktopTheme
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@ExperimentalFoundationApi
@Composable
fun scrollbarView() {
    Row(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.weight(1f)) {
            Text("Scrollbars")
            scrollbars()
        }
        Column(modifier = Modifier.weight(1f)) {
            Text("Lazy Scrollable")
            lazyScrollable()
        }
        Column(modifier = Modifier.weight(1f)) {
            Text("Theme Applying")
            themeApplying()
        }
    }
}

@Composable
fun scrollbars() {
    Box(
        modifier = Modifier.fillMaxSize()
            .background(color = Color(180, 180, 180))
            .padding(10.dp)
    ) {
        val stateVertical = rememberScrollState(0f)
        val stateHorizontal = rememberScrollState(0f)

        ScrollableColumn(
            modifier = Modifier.fillMaxSize()
                .padding(end = 12.dp, bottom = 12.dp),
            scrollState = stateVertical
        ) {
            ScrollableRow(scrollState = stateHorizontal) {
                Column {
                    for (item in 0..30) {
                        textBox("Item in ScrollableColumn #$item")
                        if (item < 30) {
                            Spacer(modifier = Modifier.height(5.dp))
                        }
                    }
                }
            }
        }
        VerticalScrollbar(
            modifier = Modifier.align(Alignment.CenterEnd)
                .fillMaxHeight(),
            adapter = rememberScrollbarAdapter(stateVertical)
        )
        HorizontalScrollbar(
            modifier = Modifier.align(Alignment.BottomStart)
                .fillMaxWidth()
                .padding(end = 12.dp),
            adapter = rememberScrollbarAdapter(stateHorizontal)
        )
    }
}

@ExperimentalFoundationApi
@Composable
fun lazyScrollable() {
    Box(
        modifier = Modifier.fillMaxSize()
            .background(color = Color(180, 180, 180))
            .padding(10.dp)
    ) {

        val state = rememberLazyListState()
        val itemCount = 1000

        LazyColumn(Modifier.fillMaxSize().padding(end = 12.dp), state) {
            items((1..itemCount).toList()) { x ->
                textBox("Item in ScrollableColumn #$x")
                Spacer(modifier = Modifier.height(5.dp))
            }
        }
        VerticalScrollbar(
            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
            adapter = rememberScrollbarAdapter(
                scrollState = state,
                itemCount = itemCount,
                averageItemSize = 37.dp // TextBox height + Spacer height
            )
        )
    }
}

@Composable
fun themeApplying() {
    DesktopTheme {
        Box(
            modifier = Modifier.fillMaxSize()
                .background(color = Color(180, 180, 180))
                .padding(10.dp)
        ) {
            val state = rememberScrollState(0f)

            ScrollableColumn(
                modifier = Modifier.fillMaxSize().padding(end = 12.dp),
                scrollState = state
            ) {
                for (item in 0..30) {
                    textBox("Item in ScrollableColumn #$item")
                    if (item < 30) {
                        Spacer(modifier = Modifier.height(5.dp))
                    }
                }
            }
            VerticalScrollbar(
                modifier = Modifier.align(Alignment.CenterEnd)
                    .fillMaxHeight(),
                adapter = rememberScrollbarAdapter(state)
            )
        }
    }
}

@Composable
fun textBox(text: String = "Item") {
    Box(
        modifier = Modifier.height(32.dp)
            .width(400.dp)
            .background(color = Color(200, 0, 0, 20))
            .padding(start = 10.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(text = text)
    }
}
