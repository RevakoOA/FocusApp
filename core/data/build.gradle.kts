plugins {
    id("focusapp.android.library")
    id("focusapp.android.library.jacoco")
    id("focusapp.android.hilt")
    id("kotlinx-serialization")
}

android {
    namespace = "com.ostapr.focusapp.core.data"
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }
}

dependencies {
    implementation(project(":core:database"))
    implementation(project(":core:model"))
    implementation(project(":core:network"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)

    testImplementation(project(":core:testing"))
}
