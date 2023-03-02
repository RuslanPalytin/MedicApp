package com.example.medicapp.navigation

sealed class OnBoardingScreenSealed(val route: String) {
    object SplashScreen: OnBoardingScreenSealed("splash_screen")
    object OnBoardingScreen: OnBoardingScreenSealed("on_boarding_screen")
    object LoginAndRegistrationScreen: OnBoardingScreenSealed("login_and_registration_screen")
    object CodeFromEmailScreen:
        OnBoardingScreenSealed("code_from_email_screen?email={email}") {
            fun passEmail(email: String): String {
                return "code_from_email_screen?email=$email"
            }
        }
    object CreatePasswordScreen: OnBoardingScreenSealed("create_password_screen")
    object CreateCardScreen: OnBoardingScreenSealed("create_card_screen")
}
