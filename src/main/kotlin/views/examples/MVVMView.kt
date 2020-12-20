package views.examples

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*

@Composable
fun mvvmView() {
    var value by remember { mutableStateOf("") }
    Column {
        Text("Name is [${value}]")
        TextField(value = value,
            onValueChange = { value = it },
            label = { Text("Name") })
    }
}
