package es.dam.loginfigma

/** Usuario registrado. Solo vive en memoria mientras la app está abierta: es una práctica de interfaz. */
data class Usuario(val nombre: String, val correo: String, val contrasena: String)

/** Resultado de las operaciones de la cuenta. */
sealed interface Resultado {
    data class Correcto(val usuario: Usuario) : Resultado
    data class Fallo(val mensaje: String) : Resultado
}

/** Almacén de cuentas en memoria (en una app real iría contra un servidor). */
class Cuentas {
    private val usuarios = mutableMapOf<String, Usuario>()

    init {
        // Cuenta de prueba para poder entrar sin registrarse.
        usuarios["demo@planea.es"] = Usuario("Demo", "demo@planea.es", "planea2026")
    }

    fun registrar(nombre: String, correo: String, contrasena: String): Resultado {
        val clave = correo.trim().lowercase()
        if (clave in usuarios) return Resultado.Fallo("Ya existe una cuenta con ese correo")
        val nuevo = Usuario(nombre.trim(), clave, contrasena)
        usuarios[clave] = nuevo
        return Resultado.Correcto(nuevo)
    }

    fun entrar(correo: String, contrasena: String): Resultado {
        val usuario = usuarios[correo.trim().lowercase()]
        return if (usuario != null && usuario.contrasena == contrasena) Resultado.Correcto(usuario)
        else Resultado.Fallo("Correo o contraseña incorrectos")
    }
}
