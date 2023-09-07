plugins {
    id("focusapp.android.feature")
    id("focusapp.android.library.compose")
    id("focusapp.android.library.jacoco")
}

android {
    namespace = "com.ostapr.focusapp.feature.status"
}

dependencies {
    implementation(libs.kotlinx.datetime)
    implementation(libs.accompanist.drawablepainter)
}