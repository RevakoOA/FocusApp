plugins {
    id("focusapp.android.library")
    id("focusapp.android.library.jacoco")
    id("focusapp.android.hilt")
}

android {
    namespace = "com.ostapr.focusapp.core.status_gatherer"
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
    implementation(project(":core:common"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.work.ktx)
    implementation(libs.hilt.ext.work)

    kapt(libs.hilt.ext.compiler)

    testImplementation(project(":core:testing"))

    androidTestImplementation(project(":core:testing"))
    androidTestImplementation(libs.androidx.work.testing)
}
