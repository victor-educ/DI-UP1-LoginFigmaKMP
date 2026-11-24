package es.dam.loginfigma

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Estilos de color de Figma (página «Estilos»). Mismo nombre y mismo valor hexadecimal.
 * En Figma: Primario #4F46E5 → aquí Color(0xFF4F46E5).
 */
object Colores {
    val Primario = Color(0xFF4F46E5)
    val PrimarioSuave = Color(0xFFE0E7FF)
    val Fondo = Color(0xFFFFFFFF)
    val Superficie = Color(0xFFF3F4F6)
    val Texto = Color(0xFF111827)
    val TextoSecundario = Color(0xFF6B7280)
    val Borde = Color(0xFFD1D5DB)
    val Error = Color(0xFFDC2626)
    val Exito = Color(0xFF059669)
}

/** Medidas que se repiten en el diseño (1 px de Figma = 1 dp). */
object Medidas {
    val MargenPantalla = 24.dp      // padding horizontal del frame
    val Separacion = 16.dp          // gap del auto layout principal
    val AltoControl = 56.dp         // campos y botones
    val Radio = 12.dp               // corner radius de campos y botones
}

/** Estilos de texto de Figma (Inter en Figma; en la app, la fuente por defecto de cada sistema). */
private val tipografia = Typography(
    headlineMedium = TextStyle(fontSize = 28.sp, lineHeight = 36.sp, fontWeight = FontWeight.Bold),
    titleMedium = TextStyle(fontSize = 16.sp, lineHeight = 24.sp, fontWeight = FontWeight.SemiBold),
    bodyLarge = TextStyle(fontSize = 16.sp, lineHeight = 24.sp),
    bodyMedium = TextStyle(fontSize = 14.sp, lineHeight = 20.sp),
    labelLarge = TextStyle(fontSize = 16.sp, lineHeight = 24.sp, fontWeight = FontWeight.SemiBold),
)

@Composable
fun TemaPlanea(contenido: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Colores.Primario,
            onPrimary = Color.White,
            primaryContainer = Colores.PrimarioSuave,
            background = Colores.Fondo,
            surface = Colores.Fondo,
            surfaceVariant = Colores.Superficie,
            onSurface = Colores.Texto,
            onSurfaceVariant = Colores.TextoSecundario,
            outline = Colores.Borde,
            error = Colores.Error,
        ),
        typography = tipografia,
        content = contenido,
    )
}
