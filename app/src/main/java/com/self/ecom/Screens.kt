package com.self.ecom

sealed class Screens(val route: String) {
    data object Auth_Screen : Screens(route = Route.AUTH_SCREEN.name)
    data object Home_Screen : Screens(route = Route.HOME_SCREEN.name)
    data object Signup_Screen : Screens(route = Route.SIGNUP_SCREEN.name)
    data object Login_Screen : Screens(route = Route.LOGIN_SCREEN.name)
    data object CategoryProducts_Screen : Screens(
        route = "${Route.CATEGORY_PRODUCTS.name}/{${ScreenArguments.CATEGORY_ID.name}}"
    ) {
        fun createRoute(categoryId: String) = "${Route.CATEGORY_PRODUCTS.name}/$categoryId"
    }
    data object Product_Details_Screen : Screens(
        route = "${Route.PRODUCT_DETAIL.name}/{${ScreenArguments.PRODUCT_ID.name}}"
    ) {
        fun createRoute(categoryId: String) = "${Route.PRODUCT_DETAIL.name}/$categoryId"
    }

    data object Checkout_Screen : Screens(route = Route.CHECKOUT_PAGE.name)
    data object OrderSuccess_Screen : Screens(route = Route.orderSuccessScreen.name)
    data object OrdersPage_Screen : Screens(route = Route.OrdersPage.name)
}

enum class Route {
    HOME_SCREEN,
    AUTH_SCREEN,
    SIGNUP_SCREEN,
    LOGIN_SCREEN,
    CATEGORY_PRODUCTS,
    PRODUCT_DETAIL,
    CHECKOUT_PAGE,
    orderSuccessScreen,
    OrdersPage
}

enum class ScreenArguments(value: String) {
    CATEGORY_ID("category_id"),
    PRODUCT_ID("product_id")
}