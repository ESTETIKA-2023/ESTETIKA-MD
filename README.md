<p align="center">
  <img src="https://raw.githubusercontent.com/ESTETIKA-2023/.github/main/profile/src/estetika-header-image.png" width="100%" height="auto" alt="ESTETIKA Header">
</p>

# ESTETIKA Mobile Development

ESTETIKA is a cultural exploration platform on a mission to unravel the enchanting world of Indonesian batik. Our vision is to not only promote the beauty of batik but also to contribute to the growth of tourism in regions celebrated for their unique batik heritage. By harnessing cutting-edge technologies, such as AI, our goal is to revolutionize the way we appreciate and engage with this traditional art form.

## Table of Contents
- [Requirements](#requirements)
- [Dependencies](#dependencies)
- [Getting Started](#getting-started)
- [Features](#features)

## Requirements
To build and run the ESTETIKA Android application, ensure you have the following software installed:

- Android Studio: 2022.3.1 (Giraffe)
- Minimum SDK: 24
- Target SDK: 34
- JDK: 1.8
- Kotlin: 1.9.0
- Android Gradle Plugin: 8.1.4
- Gradle: 8.0

## Dependencies
The project utilizes several libraries and frameworks to enhance its functionality. Here are the key dependencies:

- [Firebase Firestore](https://firebase.google.com/docs/firestore): A flexible, scalable database for mobile, web, and server development from Firebase and Google Cloud.

- [Dagger Hilt](https://dagger.dev/hilt/): A dependency injection framework that simplifies the management of dependencies in the app, promoting modularity and testability. 
  
- [Google Analytics](https://developers.google.com/analytics/solutions/mobile): Offers app measurement solution, provides insight on app usage and user engagement.

- [Retrofit](https://square.github.io/retrofit/): A type-safe HTTP client for making network requests and interacting with RESTful APIs, facilitating seamless communication with backend services.

- [Kotlin Flow](https://developer.android.com/kotlin/flow?hl=id): Provides a streamlined way to handle asynchronous data streams in a reactive programming style, enabling efficient and responsive data handling in the app.

## Getting Started
To build and run the project locally, follow these steps:

1. Clone the repository: `git clone https://github.com/ESTETIKA-2023/ESTETIKA-MD.git`
2. Open the project in Android Studio.
3. Ensure that the required SDK versions and dependencies are installed.
4. Build and run the app on an emulator or physical device.

Feel free to explore the codebase and make any contributions or improvements as needed. We appreciate your contributions to ESTETIKA!

## Features
The ESTETIKA Android application offers the following key features:

- User authentication using ESTETIKA-API, allowing users to create accounts and securely log in.
- Dependency injection with Dagger Hilt, promoting modularity and testability of the codebase.
- Camera and gallery functionality, enabling users to pick or capture an image within the app.
- Integration of Firebase Firestore, providing a flexible, scalable database for mobile.
- Network requests and API interactions facilitated by Retrofit, ensuring seamless communication with backend services. ESTETIKA-API is used for authentication and image prediction.
- Asynchronous data handling with Kotlin Flow, allowing for responsive and reactive programming.

Please refer to the codebase and documentation for more detailed information on each feature.
