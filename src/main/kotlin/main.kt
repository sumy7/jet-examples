import androidx.compose.desktop.AppManager
import androidx.compose.desktop.Window
import androidx.compose.ui.input.key.ExperimentalKeyInput
import androidx.compose.ui.window.Menu
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.MenuItem
import views.mainView

@ExperimentalKeyInput
fun main() {
    Window(title = "JetExample",
        menuBar = MenuBar(
            Menu("Actions", MenuItem("Exit", onClick = {
                AppManager.exit()
            }))
        )
    ) {
        mainView()
    }
}
