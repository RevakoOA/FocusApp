plugins {
    id("focusapp.android.library")
    id("focusapp.android.library.jacoco")
    id("focusapp.android.hilt")
}

android {
    namespace = "com.ostapr.focusapp.core.common"
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
    testImplementation(project(":core:testing"))
}