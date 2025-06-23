package com.self.ecom

sealed class Screens(val route: String) {
    data object Auth_Screen : Screens(route = Route.AUTH_SCREEN.name)
    data object Home_Screen : Screens(route = Route.HOME_SCREEN.name)
    data object Signup_Screen : Screens(route = Route.SIGNUP_SCREEN.name)
    data object Login_Screen : Screens(route = Route.LOGIN_SCREEN.name)
    data object CategoryProducts_Screen : Screens(route = Route.CATEGORY_PRODUCTS.name)

}

enum class Route {
    HOME_SCREEN,
    AUTH_SCREEN,
    SIGNUP_SCREEN,
    LOGIN_SCREEN,
    CATEGORY_PRODUCTS
}

enum class ScreenArguments(name: String) {
    CATEGORY_ID("category_id")
}