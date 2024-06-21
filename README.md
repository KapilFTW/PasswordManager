# PasswordManager

A Basic Password Manager with Data Encryption

## Overview

This Password Manager app provides a secure way to store and manage your passwords. It encrypts your data and ensures that your sensitive information is kept safe. The app follows the MVVM architecture and uses Room DB for local data storage. It also includes biometric authentication for an added layer of security.

## Screenshots
![image](https://github.com/KapilFTW/PasswordManager/assets/87802829/f58375ff-e9da-4747-b815-d8bf9922cedb) ![image](https://github.com/KapilFTW/PasswordManager/assets/87802829/5bb17a1b-852b-453a-8e87-5039f98e6e21) ![image](https://github.com/KapilFTW/PasswordManager/assets/87802829/5d328ad7-6dda-41d0-8ba1-929af25a96d5) ![image](https://github.com/KapilFTW/PasswordManager/assets/87802829/7064cf8c-59a2-484d-a1d5-5a7f0ef60f7d)



## Features

- **Add Password**: Easily add new passwords to your vault.
- **Edit Password**: Update your existing passwords.
- **Password Encryption**: Your passwords are securely encrypted.
- **Password Strength Meter**: Get real-time feedback on the strength of your passwords.
- **Password Generator**: Generate strong and secure passwords.
- **Biometric Authentication**: Use biometric authentication to unlock the app.
- **Live Data Updates**: Using Kotlin Flows for real-time updates.

## Technologies Used

- **Kotlin**: Programming language used for app development.
- **Jetpack Compose**: Modern UI toolkit for building native Android UIs.
- **Room DB**: Local database for storing passwords.
- **Hilt**: Dependency injection.
- **Biometric Authentication**: For secure app access.
- **MVVM Architecture**: For a clean and maintainable codebase.

## Project Structure

### Activities

- `MainActivity`: The main entry point of the app.
- `SplashActivity`: The splash screen that handles biometric authentication.

### Composables

- `HomeScreen`: Handles displaying, adding, and editing passwords.

### ViewModel

- `HomeViewModel`: Manages the UI-related data and handles interactions for `HomeScreen`.

### Database

- **Entities**: `Password` data class.
- **DAO**: Data Access Object for Room DB.
- **Repository**: Manages data operations and provides a clean API for data access.

## How to Use

1. **Clone the Repository**: 
   ```bash
   git clone https://github.com/KapilFTW/PasswordManager.git
   ```
2. **Open in Android Studio**: Import the project into Android Studio
3. **Build the Project**: Let Gradle sync and build the project.
4. **Run the App**: Deploy the app to an emulator or physical device.
