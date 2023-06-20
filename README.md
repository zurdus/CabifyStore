## Cabify Store

| <img src="https://github.com/zurdus/CabifyStore/assets/6010616/f23ad0eb-12ca-4d7f-bc6b-d53487951f87" width="250" height="555.5">| <img src="https://github.com/zurdus/CabifyStore/assets/6010616/8269187e-2e07-40f9-85d1-057a3f51189a" width="250" height="555.5">|
|--|--|

A simple application made to showcase a variety of modern tools and architectural design for Android development.

The Cabify Store features a product catalog that users can interact with. They can add products to a shopping cart and then review or modify their order before proceeding to checkout. To boost sales, several discounts are applied during the checkout process. The discounted prices are reflected in both the product details and the summary of the order.

## Architecture and modularization

Regardless of its current scope, the app is designed to scale easily and accommodate new features in the future. Its modularization approach allows for better organization and smoother addition of future functionalities.

### UI Layer

Implemented via the `:feature:catalog` and `:feature:checkout` modules. This layer is written using Jetpack Compose and uses Flows for communication between ViewModels and Screens.

The ViewModels hold references to pertinent use cases, which are implemented in the `:domain:*` modules. Every module handles its internal navigation, exposes its own graph, and rely on the `:navigation` module for inter-module navigation.

### Domain layer

These Kotlin modules contain the use cases with which the UI layer will interact with the data. It has two divisions: `:domain:catalog` and `:domain:checkout`, which are related to catalog and checkout functionalities respectively.

### Data layer

The Data layer is organized based on business entities rather than features. It encompasses the `:data:product`, `:data:cart` and `:data:discount` modules. Each data module is further separated in a `api` Kotlin module which exposes data-related interfaces, and an `impl` Android module that implements those interfaces. Domain modules can only access the `api` modules.

#### Product

These modules contain `ProductRepository`, which integrates both remote (with [Retrofit](https://square.github.io/retrofit/)) and local (with [Jetpack DataStore](https://developer.android.com/topic/libraries/architecture/datastore)) data sources. A simple caching logic is implemented, where every remote response is stored locally as a fallback.

#### Cart

These modules contain the `CartSource` definition and its implementation using Jetpack Datastore.

#### Discount
These modules contain a very simple `DiscountRepository` holding a `DiscountDataSource`, The latter is currently implemented as Kotlin class with no persistence, but can easily be adapter to use remote configuration or other more flexible approaches.

### Base modules

The application also includes several base modules to handle functionalities that are relevant for different aspects of the app:

- `:base:common` holds classes that are not business models, are necessary for several modules. It currently contains the `Response` interface for data responses.
- `:base:model` contains the business classes for the application.
- `:base:ui` contains common composables and the app Theme definition, designed following the Cabify brand guidelines (to the best of my abilities).
- `:base:util` comprises extension functions and dispatchers definition.

### App module

This module handles the dependency injection through [Koin](https://insert-koin.io/), and serves as the primary point for the navigation system.

## Testing

Unit testing is implemented using JUnit and [MockK](https://mockk.io/) for stubs and [Hamcrest](https://hamcrest.org/JavaHamcrest/index) for assertions. Unit tests are primarily focused on data and domain modules

## Future Enhancements

- Dark theme support
- Better landscape mode compatibility
- User tracking
- Crash reporting
- Integration of static analysis tools
- Extension of unit, instrumented and screenshot Testing in feature modules
