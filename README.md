# CSTV App

The CSTV app show a list with the running and upcoming CS2 matches. It allows you to see the match details like players and scheduled time.

## Architecture

The architecture of the app follows the best practices and recommended architecture for modern app development from the [android developer guide](https://developer.android.com/topic/architecture).

The overall architecture looks as shown on the image below:

<img src="https://developer.android.com/topic/libraries/architecture/images/mad-arch-overview.png" width="360" height="240">

The app has three main well-defined layers, implementing the [MVVM architecture pattern](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel), is separated into packages where each package contains a child feature package with each feature's components. The feature packages are `mainlist` and `matchdetails`.

- **Presentation**:
    - The Presentation layer, also known as UI layer, is responsible for displaying the application data on the screen and also serve as the primary entry point for user interaction.
    - Inside the Presentation layer the app has the MainActivity which shows the Splash Screen and adds the Fragments for each feature/screen in the app.
    - In this layer the app has the `Activities`, `Fragments` and its `ViewModels` and `Adapters` for the lists used.
- **Data**:
    - The Data layer connects to the Panda Score API pointing to CS:GO endpoints.
    - Inside the Data layer the app has the implementation of the domain's repository contract to fetch the data. In this example data is fetched from the api which is accessed using the Retrofit library under the remote package. Finally there is a mapper which is responsible for mapping the response we get from the API, here the app uses a `DTO` and `Entity` types of classes to convert between remote and domain models in order for the domain models to not have any dependency to the remote implementation selected.
- **Domain**:
    - The domain Layer contains some core classes that are used on the App.
    - There is the `DataResponse` used to pass the data response from data layer to presentation layer whereas Success is used after the execution returned successfully and Error is used when an error occurred during execution.
    - Each feature has a package following the app architecture with its model and its repository's contract.

Overall this architecture is very robust, follows the official android developer guideline and makes it easy to maintain and scale the application.

## Unit Tests

For unit testing there is a unit test for each feature view model (`MainListViewModelTest` and `MatchDetailsViewModelTest`) which is where the business logic of the application is.

## Instruction to build the project

This is a standard Android application, there are no special setup need. Just import the project into Android Studio and run the App.

## Demo

[Watch the demo video on Google Drive](https://drive.google.com/file/d/12P04Ai1y4wryNjQsyq-0Nxqz0cONPt87/view?usp=drive_link)

<img src="https://github.com/carlosolimpio/cstv-app/assets/11680359/8ef80e0d-3805-4e99-8e65-28d1fb99c070" alt="drawing" width="300"/>
<img src="https://github.com/carlosolimpio/cstv-app/assets/11680359/9cb8cb72-d450-45c8-ae8d-dcdf5fb16b90" alt="drawing" width="300"/>


## Libraries used

The project was developed using some of the Android Jetpack Libraries and some third-party libraries as well.

### Android Jetpack Libraries
- View Binding: Feature that allows to write code that interacts with views, replacing the `findViewById`. For more details see the [official docs](https://developer.android.com/topic/libraries/view-binding).
- Paging3: The Paging Library makes it easier for you to load data gradually and gracefully within your app's RecyclerView. See the [official page](https://developer.android.com/jetpack/androidx/releases/paging).
- SwipeRefreshLayout: A widget to implement the swipe to refresh UI-pattern. [Docs](https://developer.android.com/jetpack/androidx/releases/swiperefreshlayout).

### Kotlin library:
- Kotlin coroutines: used to manage long-running tasks that might otherwise block the main thread. For more information see [The Kotlin coroutines official doc for Android](https://developer.android.com/kotlin/coroutines)
- Kotlin coroutines flow: used to emit multiple values sequentially from the data layer to the presentation layer. For more details check [Kotlin flows on Android](https://developer.android.com/kotlin/flow)

### Dependency Injection
- Hilt: dependency injection library for Android that reduces the boilerplate of doing manual dependency injection in the project. For more details see the [Dependency injection with Hilt](https://developer.android.com/training/dependency-injection/hilt-android)

### Third-party libraries:
- SplashScreen API: Library to implement splash screen easily on Android. Check [Android official docs](https://developer.android.com/develop/ui/views/launch/splash-screen).
- Retrofit + OkHttp: Http clients to simplify the communication with the [Panda Score Docs](https://developers.pandascore.co/docs). [Official docs](https://square.github.io/retrofit/)
- Glide: Used to load the url images into the ImageView for the events. [Official docs](https://github.com/bumptech/glide)

### Testing libraries:
- MockK: a mocking library for Kotlin. [Official website](https://mockk.io/)
- Turbine: a library to test kotlinx.coroutines flows. [Official repository and docs](https://github.com/cashapp/turbine)
