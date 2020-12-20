package views.examples

import androidx.compose.animation.ColorPropKey
import androidx.compose.animation.DpPropKey
import androidx.compose.animation.core.*
import androidx.compose.animation.transition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonConstants
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.unit.dp
import views.examples.ButtonState.IDLE
import views.examples.ButtonState.PRESSED

enum class ButtonState {
    IDLE, PRESSED
}


val outlineLove = vectorBuilder().addPath(
    pathData = parsePath("M20.84 4.61a5.5 5.5 0 0 0-7.78 0L12 5.67l-1.06-1.06a5.5 5.5 0 0 0-7.78 7.78l1.06 1.06L12 21.23l7.78-7.78 1.06-1.06a5.5 5.5 0 0 0 0-7.78z"),
    fill = SolidColor(Color.Blue)
).build()


private fun vectorBuilder() = ImageVector.Builder(
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f
)

private fun parsePath(pathStr: String) = PathParser().parsePathString(pathStr).toNodes()


val purple500 = Color(156, 39, 179)

val width = DpPropKey()
val roundedCorners = IntPropKey()
val backgroundColor = ColorPropKey()
val textColor = ColorPropKey()

@Composable
fun animationView() {
    val buttonState = remember { mutableStateOf(IDLE) }

    val transitionDefinition = transitionDefinition<ButtonState> {
        state(IDLE) {
            this[width] = 300.dp
            this[roundedCorners] = 6
            this[textColor] = purple500
            this[backgroundColor] = Color.White
        }

        state(PRESSED) {
            this[width] = 60.dp
            this[roundedCorners] = 50
            this[textColor] = Color.White
            this[backgroundColor] = purple500
        }

        transition(IDLE to PRESSED) {
            width using tween(durationMillis = 500)
            roundedCorners using tween(
                durationMillis = 500,
                easing = FastOutLinearInEasing
            )
            backgroundColor using tween(durationMillis = 500)
            textColor using tween(durationMillis = 500)
        }

        transition(PRESSED to IDLE) {
            width using tween(durationMillis = 500)
            roundedCorners using tween(
                durationMillis = 500,
                easing = FastOutLinearInEasing
            )
            backgroundColor using tween(durationMillis = 500)
            textColor using tween(durationMillis = 500)
        }
    }

    var beforeState by remember { mutableStateOf(IDLE) }

    val state = transition(
        definition = transitionDefinition,
        initState = beforeState,
        toState = buttonState.value
    ) {
        beforeState = it
    }

    Column {
        Text("State -> ${buttonState.value}")
        favButton(buttonState = buttonState, state = state)

    }
}

@Composable
fun favButton(
    buttonState: MutableState<ButtonState>,
    state: TransitionState
) {
    Button(
        border = BorderStroke(1.dp, purple500),
        colors = ButtonConstants.defaultButtonColors(backgroundColor = state[backgroundColor]),
        shape = RoundedCornerShape(state[roundedCorners]),
        modifier = Modifier.size(state[width], 60.dp),
        onClick = {
            buttonState.value = when (buttonState.value) {
                PRESSED -> IDLE
                IDLE -> PRESSED
            }
        }
    ) {
        buttonContent(buttonState, state)
    }
}

@Composable
fun buttonContent(
    buttonState: MutableState<ButtonState>,
    state: TransitionState
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(
            modifier = Modifier.width(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                outlineLove,
                tint = state[textColor],
                modifier = Modifier.size(24.dp)
            )
        }
        if (buttonState.value == IDLE) {
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                "ADD TO FAVORITES!",
                softWrap = false,
                color = state[textColor]
            )
        }
    }
}
