plugins {
    id("org.sonarqube") version "3.5.0.2730"
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.bj.securecode"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.bj.securecode"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    lint {
        htmlReport = true
        htmlOutput = file("${layout.buildDirectory}/reports/lint/lint-report.html")

        xmlReport = true
        xmlOutput = file("${layout.buildDirectory}/reports/lint/lint-report.xml")

        textReport = true
        textOutput = file("${layout.buildDirectory}/reports/lint/lint-report.txt")

        abortOnError = false
        warningsAsErrors = false
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    lintChecks(project(":lint-rules"))

    //this should dependency rejected
    implementation("com.vanniktech:android-image-cropper:4.3.3")
}

sonarqube {
    properties {
        property("sonar.projectKey", "basimJm_pipelines-sample-project")
        property("sonar.organization", "basimjm")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.sources", "src/main/java")
        property("sonar.tests", "src/test/java")
        property("sonar.java.binaries", "build/intermediates/javac")
        property("sonar.kotlin.version", "2.0.0")
        property("sonar.language", "kotlin")
    }
}