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
include(":feature:cart")

include(":domain:product")
include(":domain:cart")

include(":data:product:api")
include(":data:product:impl")

include(":data:cart:api")
include(":data:cart:impl")

include(":util")
include(":base")
include(":base-ui")
