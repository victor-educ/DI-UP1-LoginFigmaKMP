package es.dam.loginfigma

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/** Frame de Figma de 360 x 800 con auto layout vertical, padding 24 y scroll si no cabe. */
@Composable
private fun Marco(contenido: @Composable () -> Unit) {
    Column(
        Modifier.fillMaxSize().background(Colores.Fondo).safeDrawingPadding().imePadding()
            .verticalScroll(rememberScrollState()).padding(horizontal = Medidas.MargenPantalla),
        verticalArrangement = Arrangement.spacedBy(Medidas.Separacion),
    ) { contenido() }
}

/** Pantalla 1 · Bienvenida. */
@Composable
fun PantallaBienvenida(alEntrar: () -> Unit, alRegistrarse: () -> Unit) {
    Column(
        Modifier.fillMaxSize().background(Colores.Fondo).safeDrawingPadding().padding(Medidas.MargenPantalla),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.weight(1f))
        // Ilustración: tarjeta de color suave con el logo (en Figma, grupo «Ilustración»).
        Box(
            Modifier.fillMaxWidth().height(260.dp).background(Colores.PrimarioSuave, RoundedCornerShape(28.dp)),
            contentAlignment = Alignment.Center,
        ) { Logo(96) }
        Spacer(Modifier.height(32.dp))
        Text("Organiza tus estudios", style = MaterialTheme.typography.headlineMedium, textAlign = TextAlign.Center)
        Spacer(Modifier.height(8.dp))
        Text(
            "Planea reúne tus tareas, exámenes y entregas en un solo sitio.",
            style = MaterialTheme.typography.bodyLarge, color = Colores.TextoSecundario, textAlign = TextAlign.Center,
        )
        Spacer(Modifier.weight(1f))
        BotonPrimario("Iniciar sesión", alEntrar)
        Spacer(Modifier.height(12.dp))
        BotonSecundario("Crear cuenta", alRegistrarse)
    }
}

/** Pantalla 2 · Iniciar sesión. */
@Composable
fun PantallaLogin(cuentas: Cuentas, alVolver: () -> Unit, alRegistrarse: () -> Unit, alEntrar: (Usuario) -> Unit) {
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var intentado by remember { mutableStateOf(false) }
    var aviso by remember { mutableStateOf<String?>(null) }
    val errorCorreo = if (intentado) Validacion.correo(correo) else null
    val errorContrasena = if (intentado && contrasena.isEmpty()) "Escribe tu contraseña" else null

    Marco {
        BotonVolver(alVolver)
        Text("Hola de nuevo", style = MaterialTheme.typography.headlineMedium)
        Text("Inicia sesión para seguir con tus tareas.", style = MaterialTheme.typography.bodyLarge, color = Colores.TextoSecundario)
        Spacer(Modifier.height(8.dp))
        CampoTexto(correo, { correo = it; aviso = null }, "Correo electrónico", "nombre@correo.com", error = errorCorreo, tipo = KeyboardType.Email)
        CampoTexto(contrasena, { contrasena = it; aviso = null }, "Contraseña", "Tu contraseña", error = errorContrasena, esContrasena = true, ultimo = true)
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            TextButton(onClick = { aviso = "Te hemos enviado un correo para cambiar la contraseña (simulado)." }) {
                Text("¿Olvidaste tu contraseña?", color = Colores.Primario)
            }
        }
        aviso?.let { Text(it, color = if (it.startsWith("Te hemos")) Colores.Exito else Colores.Error, style = MaterialTheme.typography.bodyMedium) }
        BotonPrimario("Iniciar sesión", {
            intentado = true
            if (Validacion.correo(correo) == null && contrasena.isNotEmpty()) {
                when (val r = cuentas.entrar(correo, contrasena)) {
                    is Resultado.Correcto -> alEntrar(r.usuario)
                    is Resultado.Fallo -> aviso = r.mensaje
                }
            }
        })
        SeparadorTexto("o continúa con")
        BotonSecundario("Continuar con Google", { aviso = "El acceso con Google no está disponible en esta versión." }, icono = { IconoGoogle() })
        Spacer(Modifier.height(8.dp))
        PieEnlace("¿No tienes cuenta?", "Regístrate", alRegistrarse)
    }
}

/** Pantalla 3 · Crear cuenta. */
@Composable
fun PantallaRegistro(cuentas: Cuentas, alVolver: () -> Unit, alEntrar: () -> Unit, alRegistrado: (Usuario) -> Unit) {
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var repetida by remember { mutableStateOf("") }
    var acepta by remember { mutableStateOf(false) }
    var intentado by remember { mutableStateOf(false) }
    var aviso by remember { mutableStateOf<String?>(null) }

    val errores = listOf(
        Validacion.nombre(nombre), Validacion.correo(correo),
        Validacion.contrasena(contrasena), Validacion.repetir(contrasena, repetida),
    )
    fun e(i: Int) = if (intentado) errores[i] else null

    Marco {
        BotonVolver(alVolver)
        Text("Crea tu cuenta", style = MaterialTheme.typography.headlineMedium)
        Text("Solo te llevará un minuto.", style = MaterialTheme.typography.bodyLarge, color = Colores.TextoSecundario)
        Spacer(Modifier.height(8.dp))
        CampoTexto(nombre, { nombre = it }, "Nombre", "Tu nombre", error = e(0))
        CampoTexto(correo, { correo = it; aviso = null }, "Correo electrónico", "nombre@correo.com", error = e(1), tipo = KeyboardType.Email)
        CampoTexto(contrasena, { contrasena = it }, "Contraseña", "Mín. 8 caracteres y un número", error = e(2), esContrasena = true)
        CampoTexto(repetida, { repetida = it }, "Repite la contraseña", "Vuelve a escribirla", error = e(3), esContrasena = true, ultimo = true)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = acepta, onCheckedChange = { acepta = it })
            Text("Acepto los términos y la política de privacidad", style = MaterialTheme.typography.bodyMedium)
        }
        if (intentado && !acepta) Text("Debes aceptar los términos", color = Colores.Error, style = MaterialTheme.typography.bodyMedium)
        aviso?.let { Text(it, color = Colores.Error, style = MaterialTheme.typography.bodyMedium) }
        BotonPrimario("Crear cuenta", {
            intentado = true
            if (errores.all { it == null } && acepta) {
                when (val r = cuentas.registrar(nombre, correo, contrasena)) {
                    is Resultado.Correcto -> alRegistrado(r.usuario)
                    is Resultado.Fallo -> aviso = r.mensaje
                }
            }
        })
        PieEnlace("¿Ya tienes cuenta?", "Inicia sesión", alEntrar)
    }
}

/** Pantalla 4 · Inicio (después de entrar). */
@Composable
fun PantallaInicio(usuario: Usuario, alSalir: () -> Unit) {
    Marco {
        Spacer(Modifier.height(24.dp))
        Text("Hola, ${usuario.nombre}", style = MaterialTheme.typography.headlineMedium)
        Text("Has iniciado sesión como ${usuario.correo}", style = MaterialTheme.typography.bodyLarge, color = Colores.TextoSecundario)
        listOf("Entrega de DI · viernes", "Examen de Acceso a Datos · lunes", "Repasar Kotlin Multiplatform").forEach {
            Box(Modifier.fillMaxWidth().background(Colores.Superficie, RoundedCornerShape(Medidas.Radio)).padding(16.dp)) {
                Text(it, style = MaterialTheme.typography.bodyLarge)
            }
        }
        BotonSecundario("Cerrar sesión", alSalir)
    }
}
