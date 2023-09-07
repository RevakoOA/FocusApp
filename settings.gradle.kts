pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "FocusApp"

include(":app")

include(":core:designsystem")
include(":core:testing")
include(":core:ui")
include(":core:model")
include(":core:database")
include(":core:data")
include(":core:network")
include(":core:common")
include(":core:status-gatherer")

include(":feature:status")
include(":core:domain")
