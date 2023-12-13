plugins {
    kotlin("jvm") version "1.9.21"
}

group = "ru.DmN.pht"
version = "1.0.0"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation("ru.DmN.siberia:Project-Siberia:1.6.2")
    implementation("ru.DmN.pht:Project-Pihta:1.4.0")
    implementation("ru.DmN.phtx:Project-PLS:1.0.0")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks {
    val fatJar = register<Jar>("fatJar") {
        dependsOn.addAll(listOf("compileJava", "compileKotlin", "processResources"))
        archiveClassifier.set("standalone")
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        manifest { attributes(mapOf("Main-Class" to "ru.DmN.pht.demo.Console")) }
        val sourcesMain = sourceSets.main.get()
        val contents = configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) } + sourcesMain.output
        from(contents)
    }

    build {
        dependsOn(fatJar)
    }

    java {
        withSourcesJar()
    }

    test {
        useJUnitPlatform()
    }
}