import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.apache.tools.ant.taskdefs.condition.Os

plugins {
    kotlin("jvm") version "1.6.0"
    application
    id("org.openjfx.javafxplugin") version "0.0.10"
    id("org.beryx.jlink") version "2.24.4"
}

// Java 16
java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(16))
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "16"
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "application")
    repositories {
        mavenCentral()
    }

    dependencies {
        implementation(kotlin("stdlib"))
    }
}

group = "io.github.dolphin2410"
version = "0.0.1"

repositories {
    mavenCentral()
}

project(":mcsr") {
    apply(plugin = "application")
    apply(plugin = "org.openjfx.javafxplugin")
    apply(plugin = "org.beryx.jlink")
    dependencies {
        implementation(kotlin("stdlib"))
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2-native-mt")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.1")
        implementation(project(":api"))
    }

    application {
        mainClass.set("io.github.dolphin2410.mcsr.Main")
        mainModule.set("McSR.main")
    }

    javafx {
        version = "16"
        modules = listOf(
            "javafx.controls", "javafx.fxml", "javafx.web"
        )
    }

    jlink {
        launcher {
            name = "McSR"
        }
        forceMerge(
            "kotlin-stdlib",
            "kotlin-stdlib-common",
            "kotlin-stdlib-jdk7",
            "kotlin-stdlib-jdk8",
            "kotlinx-coroutines-core-jvm",
            "kotlinx-serialization-core-jvm",
            "kotlinx-serialization-json-jvm"
        )
        addExtraDependencies("javafx")
        imageZip.set(project.file("${project.buildDir}/${project.name}-bin.zip"))
        jpackage {
            installerName = "McSR Installer"
        }
    }
}

tasks {

    val binaryTask = create("binary") {
        dependsOn(jlinkZip.get())
    }

//    val packager = jpackage.get()
//
//    create("windows") {
//        if (Os.isFamily(Os.FAMILY_WINDOWS)) {
//            packager.jpackageData.installerType = "msi"
//            dependsOn(binaryTask)
//            dependsOn(packager)
//        }
//    }
//
//    create("debian") {
//        if (Os.isFamily(Os.FAMILY_UNIX)) {
//            packager.jpackageData.installerType = "deb"
//            dependsOn(binaryTask)
//            dependsOn(packager)
//        }
//    }
//
//    create("redhat") {
//        if (Os.isFamily(Os.FAMILY_UNIX)) {
//            packager.jpackageData.installerType = "rpm"
//            dependsOn(binaryTask)
//            dependsOn(packager)
//        }
//    }
//
//    create("mac") {
//        if (Os.isFamily(Os.FAMILY_MAC)) {
//            packager.jpackageData.installerType = "dmg"
//            dependsOn(binaryTask)
//            dependsOn(packager)
//        }
//    }
}