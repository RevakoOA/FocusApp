plugins {
    id("focusapp.android.feature")
    id("focusapp.android.library.compose")
    id("focusapp.android.library.jacoco")
}

android {
    namespace = "com.ostapr.focusapp.feature.status"
}

dependencies {
    implementation(project(":core:designsystem"))

    implementation(libs.androidx.activity.compose)
}
