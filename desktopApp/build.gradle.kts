import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

dependencies {
    implementation(project(":shared"))
    implementation(compose.desktop.currentOs)
    implementation(libs.kotlinx.coroutines.swing)
}

compose.desktop {
    application {
        mainClass = "es.dam.loginfigma.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Deb, TargetFormat.Msi, TargetFormat.Dmg)
            packageName = "LoginFigmaKMP"
            packageVersion = "1.0.0"
        }
    }
}

// Capturas de las pantallas para el documento (ver Capturas.kt).
tasks.register<JavaExec>("capturas") {
    group = "documentacion"
    mainClass.set("es.dam.loginfigma.CapturasKt")
    classpath = sourceSets["main"].runtimeClasspath
    args(rootProject.file("capturas").path)
}
