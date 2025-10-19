import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `java-library`
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly("com.android.tools.lint:lint-api:31.2.2")
    testImplementation("com.android.tools.lint:lint-tests:31.2.2")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

tasks.named<Jar>("jar") {
    manifest {
        attributes(
            mapOf("Lint-Registry-v2" to "com.bj.securecode.lint_rules.LintRegistry")
        )
    }
}
