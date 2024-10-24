Weather Application
A sleek and modern weather app that allows users to check current weather conditions for any city. The app displays the temperature, weather conditions, wind speed, sunrise and sunset times in a visually appealing circular layout.

Features
Current Weather Data: Displays the current temperature, humidity, wind speed, and weather conditions.
Sunrise & Sunset Times: Shows the exact times for sunrise and sunset.
City Search: Users can search for weather information for any city by entering its name, City Code or State Code.
Cached Data: The app caches the last weather data and automatically loads it upon app restart.
Beautiful UI: A visually appealing interface with a circular weather dial and detailed information.

Technologies Used
Kotlin: Programming language used for the Android app development.
Jetpack Compose: Used to build the UI using declarative programming.
Retrofit: A type-safe HTTP client for making network requests to fetch weather data from the OpenWeather API.
Room Database: Used for local data storage, caching the last weather data.
Hilt (Dagger): Used for dependency injection to manage the app's components.
LiveData & ViewModel: Used to handle and observe data changes and lifecycle events efficiently.
Junit: Used for End to End Testing for the Application
Mockk: Which allows you to create and stub objects within your test code
Test Cases:
    ViewModel Testing
        Fetching Details From the Api
        Throwing Exception when failed to load data from APi
        Saving the data into Room database from Api
        Loading the data from Room database
        Throwing Exception when failed to load data from database
    Repository Testing
        Fetching Details From the Api
        Throwing Exception when failed to load data from APi
Coroutines: computer program components that allow execution to be suspended and resumed, generalizing subroutines for cooperative multitasking


Architecture
This project follows the Clean with MVVM (Model-View-ViewModel) architecture, ensuring a clear separation of concerns and easier testing and maintenance.

Components
Data: Data models for weather information.
ViewModel: Acts as a communication layer between the repository and the UI. Handles logic and data for fetching and caching weather data.
UI: The Jetpack Compose UI components display the weather information in a clean and user-friendly layout.

API Integration
This application uses the OpenWeatherMap API to fetch real-time weather data. You will need an API key to use this service.

API Endpoint: https://api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}
