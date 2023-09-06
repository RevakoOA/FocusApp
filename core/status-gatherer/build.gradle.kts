plugins {
    id("focusapp.android.library")
    id("focusapp.android.library.jacoco")
    id("focusapp.android.hilt")
    id("kotlinx-serialization")
}

android {
    namespace = "com.ostapr.focusapp.core.status-gatherer"
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:data"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)
    implementation(libs.androidx.work.runtime.ktx)

    testImplementation(project(":core:testing"))
}
