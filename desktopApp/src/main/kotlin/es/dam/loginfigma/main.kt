package es.dam.loginfigma

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

// Ventana del tamaño del frame de Figma (360 x 800) para comparar el diseño con la app.
fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Planea",
        state = rememberWindowState(width = 360.dp, height = 800.dp),
    ) {
        App()
    }
}
