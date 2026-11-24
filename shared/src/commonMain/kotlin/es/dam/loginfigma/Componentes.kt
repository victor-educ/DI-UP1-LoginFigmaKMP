package es.dam.loginfigma

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/*
 * Componentes de Figma (página «Componentes») convertidos en funciones @Composable.
 * Cada propiedad del componente de Figma es un parámetro de la función.
 */

/** Componente «Logo»: círculo de color primario con la inicial. */
@Composable
fun Logo(tamano: Int = 72) {
    Box(
        Modifier.size(tamano.dp).background(Colores.Primario, RoundedCornerShape(20.dp)),
        contentAlignment = Alignment.Center,
    ) {
        Text("P", color = Color.White, fontSize = (tamano / 2).sp, fontWeight = FontWeight.Bold)
    }
}

/**
 * Componente «Campo de texto». Variantes de Figma: tipo = texto | correo | contraseña,
 * estado = normal | error. La variante «error» es el parámetro [error] distinto de null.
 */
@Composable
fun CampoTexto(
    valor: String,
    alCambiar: (String) -> Unit,
    etiqueta: String,
    pista: String,
    modifier: Modifier = Modifier,
    error: String? = null,
    tipo: KeyboardType = KeyboardType.Text,
    esContrasena: Boolean = false,
    ultimo: Boolean = false,
) {
    var visible by remember { mutableStateOf(false) }
    // Auto layout vertical de Figma: etiqueta encima (gap 6) y caja de 56 de alto.
    androidx.compose.foundation.layout.Column(modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(6.dp)) {
    Text(etiqueta, style = MaterialTheme.typography.bodyMedium, color = if (error != null) Colores.Error else Colores.TextoSecundario)
    OutlinedTextField(
        value = valor,
        onValueChange = alCambiar,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(pista, color = Colores.TextoSecundario) },
        singleLine = true,
        isError = error != null,
        supportingText = error?.let { { Text(it) } },
        shape = RoundedCornerShape(Medidas.Radio),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Colores.Borde,
            focusedBorderColor = Colores.Primario,
            unfocusedContainerColor = Colores.Superficie,
            focusedContainerColor = Colores.Fondo,
        ),
        visualTransformation = if (esContrasena && !visible) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(
            keyboardType = if (esContrasena) KeyboardType.Password else tipo,
            imeAction = if (ultimo) ImeAction.Done else ImeAction.Next,
        ),
        trailingIcon = if (esContrasena) {
            { TextButton(onClick = { visible = !visible }) { Text(if (visible) "Ocultar" else "Ver") } }
        } else null,
    )
    }
}

/** Componente «Botón» variante primario (relleno). Alto 56, radio 12, ancho fill container. */
@Composable
fun BotonPrimario(texto: String, alPulsar: () -> Unit, modifier: Modifier = Modifier, activo: Boolean = true) {
    Button(
        onClick = alPulsar,
        enabled = activo,
        modifier = modifier.fillMaxWidth().height(Medidas.AltoControl),
        shape = RoundedCornerShape(Medidas.Radio),
        colors = ButtonDefaults.buttonColors(containerColor = Colores.Primario),
    ) { Text(texto, style = MaterialTheme.typography.labelLarge) }
}

/** Componente «Botón» variante secundario (borde). */
@Composable
fun BotonSecundario(texto: String, alPulsar: () -> Unit, modifier: Modifier = Modifier, icono: (@Composable () -> Unit)? = null) {
    OutlinedButton(
        onClick = alPulsar,
        modifier = modifier.fillMaxWidth().height(Medidas.AltoControl),
        shape = RoundedCornerShape(Medidas.Radio),
        border = BorderStroke(1.dp, Colores.Borde),
    ) {
        if (icono != null) {
            icono()
            Box(Modifier.size(12.dp))
        }
        Text(texto, style = MaterialTheme.typography.labelLarge, color = Colores.Texto)
    }
}

/** Icono «G» del botón de Google (en Figma es un círculo con la letra). */
@Composable
fun IconoGoogle() {
    Box(Modifier.size(24.dp).background(Colores.Superficie, CircleShape), contentAlignment = Alignment.Center) {
        Text("G", fontWeight = FontWeight.Bold, color = Color(0xFFEA4335), fontSize = 14.sp)
    }
}

/** Componente «Separador con texto»: línea — texto — línea. */
@Composable
fun SeparadorTexto(texto: String) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        HorizontalDivider(Modifier.weight(1f), color = Colores.Borde)
        Text(texto, style = MaterialTheme.typography.bodyMedium, color = Colores.TextoSecundario)
        HorizontalDivider(Modifier.weight(1f), color = Colores.Borde)
    }
}

/** Pie con pregunta y enlace: «¿No tienes cuenta? Regístrate». */
@Composable
fun PieEnlace(pregunta: String, enlace: String, alPulsar: () -> Unit) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
        Text(pregunta, style = MaterialTheme.typography.bodyMedium, color = Colores.TextoSecundario)
        TextButton(onClick = alPulsar, contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 4.dp)) {
            Text(enlace, fontWeight = FontWeight.SemiBold, color = Colores.Primario)
        }
    }
}

/** Botón «←» de volver de la barra superior. */
@Composable
fun BotonVolver(alPulsar: () -> Unit) {
    TextButton(onClick = alPulsar, modifier = Modifier.padding(top = 8.dp)) {
        Text("←  Volver", color = Colores.Texto, style = MaterialTheme.typography.titleMedium)
    }
}
