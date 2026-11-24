package es.dam.loginfigma

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

/** Las pantallas del prototipo de Figma. Las flechas del prototipo son los cambios de este estado. */
sealed interface Pantalla {
    data object Bienvenida : Pantalla
    data object Login : Pantalla
    data object Registro : Pantalla
    data class Inicio(val usuario: Usuario) : Pantalla
}

@Composable
fun App() {
    val cuentas = remember { Cuentas() }
    var pantalla by remember { mutableStateOf<Pantalla>(Pantalla.Bienvenida) }
    TemaPlanea {
        when (val p = pantalla) {
            Pantalla.Bienvenida -> PantallaBienvenida(
                alEntrar = { pantalla = Pantalla.Login },
                alRegistrarse = { pantalla = Pantalla.Registro },
            )
            Pantalla.Login -> PantallaLogin(
                cuentas,
                alVolver = { pantalla = Pantalla.Bienvenida },
                alRegistrarse = { pantalla = Pantalla.Registro },
                alEntrar = { pantalla = Pantalla.Inicio(it) },
            )
            Pantalla.Registro -> PantallaRegistro(
                cuentas,
                alVolver = { pantalla = Pantalla.Bienvenida },
                alEntrar = { pantalla = Pantalla.Login },
                alRegistrado = { pantalla = Pantalla.Inicio(it) },
            )
            is Pantalla.Inicio -> PantallaInicio(p.usuario, alSalir = { pantalla = Pantalla.Bienvenida })
        }
    }
}
