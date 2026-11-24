package es.dam.loginfigma

/** Reglas de los formularios. Están separadas de la interfaz para poder probarlas con tests. */
object Validacion {
    private val formatoCorreo = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")

    /** Devuelve el mensaje de error del campo, o null si es correcto. */
    fun nombre(valor: String): String? =
        if (valor.trim().length < 2) "Escribe tu nombre" else null

    fun correo(valor: String): String? = when {
        valor.isBlank() -> "Escribe tu correo"
        !formatoCorreo.matches(valor.trim()) -> "El correo no es válido"
        else -> null
    }

    fun contrasena(valor: String): String? = when {
        valor.length < 8 -> "Mínimo 8 caracteres"
        valor.none { it.isDigit() } -> "Debe incluir al menos un número"
        else -> null
    }

    fun repetir(contrasena: String, repetida: String): String? =
        if (contrasena != repetida) "Las contraseñas no coinciden" else null
}
