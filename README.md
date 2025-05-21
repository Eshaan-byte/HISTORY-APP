🏛️ Historical App
An Android application that demonstrates best practices in modern Android development including API integration, clean architecture, and lifecycle-aware UI design.

📱 Features
🔐 Login Screen – Authenticates users using a username and password.

🗓️ Dashboard Screen – Displays a list of historical events fetched from a remote API.

📜 Details Screen – Shows in-depth information about selected historical events.

🏗️ Architecture & Technologies
This project is built using modern Android architecture components and tools:

Clean Architecture – Structured with a clear separation of concerns across data, domain, and presentation layers.
MVVM (Model-View-ViewModel) – Ensures a clean and testable codebase.
Hilt – Simplifies dependency injection and lifecycle management.
Retrofit – For efficient and type-safe HTTP communication.
Kotlin Coroutines – Handles asynchronous operations smoothly.
LiveData & ViewModel – Ensures UI components reactively respond to data changes.
ViewBinding – Enables type-safe interaction with layout views.

🌐 API Integration
🔑 Login Endpoint
Base URL: https://nit3213api.onrender.com
Endpoint: /sydney/auth
Method: POST
Request Body (JSON):

json
{
  "username": "Eshaan",
  "password": "s8093457"
}
📋 Dashboard Endpoint
Endpoint: /dashboard/{keypass}

Method: GET

Replace {keypass} with the token received from the login response.

✅ Testing
Unit tests are provided for key components like ViewModel logic.

Run Tests in Android Studio:
Open the project in Android Studio.

Navigate to the test directory.

Right-click on the folder and select Run Tests.

📂 Project Structure Highlights

HistoricalApp/
├── data/          # Remote and local data sources
│   ├── repository/
│   └── model/
├── domain/        # Business logic and core models
│   ├── usecase/
│   └── model/
├── presentation/  # UI layer: Activities, Fragments, ViewModels
│   ├── login/
│   ├── dashboard/
│   └── details/
├── di/            # Hilt modules for dependency injection
├── network/       # Retrofit API service definitions
└── utils/         # Utility classes and helpers


🛠️ Requirements
Android Studio Hedgehog or later
Kotlin 1.8+
Gradle 8+
Minimum SDK: 24

🤝 Contributions
Feel free to open issues or submit pull requests if you'd like to improve or extend the functionality!
