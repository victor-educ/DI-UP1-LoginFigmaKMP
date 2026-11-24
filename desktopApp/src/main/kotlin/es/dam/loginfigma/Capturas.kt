package es.dam.loginfigma

import androidx.compose.runtime.Composable
import androidx.compose.ui.ImageComposeScene
import androidx.compose.ui.unit.Density
import org.jetbrains.skia.EncodedImageFormat
import java.io.File

/**
 * Genera capturas PNG de cada pantalla (360 x 800 dp a 2x), sin abrir ventanas,
 * para compararlas con los frames de Figma. Uso: ./gradlew :desktopApp:capturas
 */
fun main(args: Array<String>) {
    val carpeta = File(args.firstOrNull() ?: "capturas").apply { mkdirs() }
    val cuentas = Cuentas()
    val demo = Usuario("Víctor", "victor@correo.es", "")
    val pantallas: List<Pair<String, @Composable () -> Unit>> = listOf(
        "01 Bienvenida" to { PantallaBienvenida({}, {}) },
        "02 Iniciar sesión" to { PantallaLogin(cuentas, {}, {}, {}) },
        "03 Crear cuenta" to { PantallaRegistro(cuentas, {}, {}, {}) },
        "04 Inicio" to { PantallaInicio(demo) {} },
    )
    for ((nombre, pantalla) in pantallas) {
        val escena = ImageComposeScene(720, 1600, Density(2f)) { TemaPlanea { pantalla() } }
        escena.render() // primer fotograma: composición
        val imagen = escena.render()
        File(carpeta, "App - $nombre.png").writeBytes(imagen.encodeToData(EncodedImageFormat.PNG)!!.bytes)
        escena.close()
        println("Captura: $nombre")
    }
}
