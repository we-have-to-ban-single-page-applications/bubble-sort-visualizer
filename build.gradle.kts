plugins {
    id("com.diffplug.spotless") version "8.0.0"
    id("org.jetbrains.kotlin.jvm") version "2.2.20"
    id("com.gradleup.shadow") version "9.2.2"
    application
}

repositories { mavenCentral() }

dependencies {
    implementation("com.formdev:flatlaf:3.6.1")
    implementation("com.miglayout:miglayout-swing:11.4.2")
}

application { mainClass = "MainKt" }

spotless {
    kotlin {
        target("*.gradle.kts", "src/**/*.kt")
        ktfmt().kotlinlangStyle()
        ktlint()
    }
    flexmark {
        target("*.MD")
        flexmark()
        endWithNewline()
    }
}
