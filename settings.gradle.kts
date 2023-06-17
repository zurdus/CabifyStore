pluginManagement {
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

rootProject.name = "Cabify Store"
include(":app")

include(":feature:catalog")
include(":feature:checkout")

include(":domain:product")
include(":domain:checkout")

include(":data:product:api")
include(":data:product:impl")

include(":data:cart:api")
include(":data:cart:impl")

include(":base:common")
include(":base:model")
include(":base:util")
include(":base:ui")

include(":navigation")
include(":data:discount:api")
include(":data:discount:impl")
