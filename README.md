# Planea · Login y registro (Figma → Kotlin Multiplatform)

Práctica de la UP1 de Desarrollo de Interfaces (2º DAM semipresencial): reproducir en Kotlin Multiplatform con Compose el diseño hecho en Figma en la tarea anterior.

- Diseño en Figma (solo lectura): https://www.figma.com/design/kLkJe6JFNvDH1lWqu88jQc/DI-UP1---Planea-login-y-registro
- Plataformas: Android y escritorio (Windows, macOS y Linux) con el mismo `commonMain`.

| Figma | App |
|---|---|
| Frame `01 Bienvenida` | `PantallaBienvenida` |
| Frame `02 Iniciar sesión` | `PantallaLogin` |
| Frame `03 Crear cuenta` | `PantallaRegistro` |
| Frame `04 Inicio` | `PantallaInicio` |
| Componente `Botón` (Tipo = Primario / Secundario / Texto) | `BotonPrimario`, `BotonSecundario`, `TextButton` |
| Componente `Campo de texto` (Tipo × Estado) | `CampoTexto(error, esContrasena)` |
| Componentes `Logo`, `Separador con texto`, `Botón Google`, `Casilla con texto` | `Logo`, `SeparadorTexto`, `BotonSecundario(icono)`, `Checkbox` |
| Estilos `Color/*` y `Texto/*` | `Colores` y `TemaPlanea` en `Tema.kt` |
| Prototipo (On click → Navigate to) | `App.kt`: estado `Pantalla` |

Las capturas de la carpeta `capturas/` las genera `./gradlew :desktopApp:capturas` sin abrir ventanas.

## Cómo ejecutarlo

1. Abrir la carpeta con Android Studio o IntelliJ IDEA (AGP 9.1, Gradle 9.3.1, JDK 17 o superior) y esperar a que sincronice.
2. Móvil: configuración `androidApp` y ▶.
3. Ordenador: panel Gradle → `desktopApp → Tasks → compose desktop → run`, o `./gradlew :desktopApp:run`.
4. Tests: `./gradlew :shared:jvmTest` (validaciones y cuentas).

Cuenta de prueba: `demo@planea.es` / `planea2026`. También se puede crear una cuenta nueva; las cuentas solo se guardan en memoria mientras la app está abierta.

## Qué hace

- Bienvenida con acceso a «Iniciar sesión» y «Crear cuenta».
- Validación de los formularios (correo, contraseña de 8 caracteres con un número, contraseñas iguales y aceptación de términos) con el estado «Error» del componente de Figma.
- Botón «Ver/Ocultar» en las contraseñas, aviso de «¿Olvidaste tu contraseña?» y de «Continuar con Google» (simulados).
- Pantalla de inicio con el nombre del usuario y cierre de sesión.

## Estructura

```
shared/src/commonMain/kotlin/es/dam/loginfigma/
  Tema.kt          estilos de color, texto y medidas de Figma
  Componentes.kt   componentes de Figma como funciones @Composable
  Pantallas.kt     los cuatro frames
  App.kt           navegación del prototipo
  Validacion.kt    reglas de los formularios
  Usuarios.kt      cuentas en memoria
shared/src/commonTest/  tests
androidApp/  arranque en Android
desktopApp/  arranque en escritorio y generador de capturas
```

Autor: Víctor Sellés Estevan.
