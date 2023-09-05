plugins {
    id("focusapp.android.library")
    id("focusapp.android.library.compose")
    id("focusapp.android.hilt")
}

android {
    namespace = "com.ostapr.core.testing"
}

dependencies {
    api(libs.androidx.compose.ui.test)
    api(libs.androidx.test.core)
    api(libs.androidx.test.rules)
    api(libs.androidx.test.runner)
    api(libs.hilt.android.testing)
    api(libs.junit4)
    api(libs.kotlinx.coroutines.test)

    debugApi(libs.androidx.compose.ui.testManifest)
}
