package es.dam.loginfigma

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ValidacionTest {
    @Test fun correoValido() = assertNull(Validacion.correo("ana@correo.es"))
    @Test fun correoSinArroba() = assertEquals("El correo no es válido", Validacion.correo("ana.correo.es"))
    @Test fun contrasenaCorta() = assertEquals("Mínimo 8 caracteres", Validacion.contrasena("abc1"))
    @Test fun contrasenaSinNumero() = assertEquals("Debe incluir al menos un número", Validacion.contrasena("abcdefgh"))
    @Test fun contrasenasDistintas() = assertEquals("Las contraseñas no coinciden", Validacion.repetir("abcdefg1", "abcdefg2"))

    @Test fun registrarYEntrar() {
        val c = Cuentas()
        assertTrue(c.registrar("Ana", "Ana@Correo.es", "segura123") is Resultado.Correcto)
        assertTrue(c.entrar("ana@correo.es", "segura123") is Resultado.Correcto)
        assertTrue(c.entrar("ana@correo.es", "otra") is Resultado.Fallo)
        assertTrue(c.registrar("Ana", "ana@correo.es", "segura123") is Resultado.Fallo)
    }
}
