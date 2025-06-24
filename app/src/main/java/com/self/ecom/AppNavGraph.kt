package com.self.ecom

import android.R.attr.category
import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.self.ecom.component.TextComponent
import com.self.ecom.pages.CategoryProductsPage
import com.self.ecom.pages.CheckoutPage
import com.self.ecom.pages.OrderSuccessScreen
import com.self.ecom.pages.OrdersPage
import com.self.ecom.pages.ProductDetailPage
import com.self.ecom.screens.AuthScreen
import com.self.ecom.screens.HomeScreen
import com.self.ecom.screens.LoginScreen
import com.self.ecom.screens.SignupScreen
import com.self.ecom.ui.theme.White
import com.self.ecom.viewmodel.CategoryViewModel
import com.self.ecom.viewmodel.PaymentViewModel

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    paymentVM: PaymentViewModel = viewModel(),
    navController: NavHostController
) {
    val context = LocalContext.current
    val isLoggedIn = Firebase.auth.currentUser != null
    val startDestination = if (isLoggedIn) Screens.Home_Screen.route else Screens.Auth_Screen.route
    val viewModel: CategoryViewModel = viewModel(context as ComponentActivity)

    val paymentSuccess = paymentVM.paymentSuccess.collectAsState()
    LaunchedEffect(paymentSuccess) {
        if (paymentSuccess.value) {
            navController.navigate("orderSuccessScreen")
            paymentVM.resetPaymentSuccess()
        }
    }

    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Screens.Auth_Screen.route) {
            AuthScreen(
                onClickSignup = {
                    navController.navigate(route = Screens.Signup_Screen.route)
                },
                onClickLogin = { navController.navigate(route = Screens.Login_Screen.route) })
        }

        composable(route = Screens.Home_Screen.route) {
            HomeScreen(
                logoutClick = {
                    navController.navigate(Screens.Auth_Screen.route) {
                        Firebase.auth.signOut()
                        popUpTo(Screens.Home_Screen.route) {
                            inclusive = true
                        }
                    }
                }, onClickCategory = { categoryModel ->
//                navController.navigate(
//                    route = Screens.CategoryProducts_Screen.route + "/${categoryModel.id}"
//                )
                    navController.navigate(Screens.CategoryProducts_Screen.createRoute(categoryModel.id))

                },
                onClickAddToCart = { productId ->
                    AppUtil.addItemToCart(
                        context = context,
                        productId = productId
                    )
                },
                onClickRemoveFromCart = { productId ->
                    AppUtil.removeItemFromCart(
                        context = context,
                        productId = productId
                    )
                },
                onClickRemoveAllCart = { productId ->
                    AppUtil.removeItemFromCart(
                        context = context,
                        productId = productId,
                        removeAll = true
                    )

                },
                onClickCheckout = {
                    navController.navigate(Screens.Checkout_Screen.route)
                },
                onClickMyOrders = {

                    navController.navigate(Screens.OrdersPage_Screen.route)
                },
                onClickSignout = {
                    FirebaseAuth.getInstance().signOut()
                    navController.popBackStack()
                    navController.navigate(Screens.Auth_Screen.route)
                })
        }
        composable(route = Screens.Signup_Screen.route) {
            SignupScreen(
                onClickSignupBtn = {
                    navController.navigate(Screens.Home_Screen.route) {
                        popUpTo(route = Screens.Auth_Screen.route) {
                            inclusive = true
                        }
                        /*
                          1. popUpTo(route = Screens.Auth_Screen.route)
                          auth>signup>home
                          on back press navigation should be
                          home>auth    , no sign up screen should be in back stack, pop stack upto auth

                         2. if we add inclusive = true, then auth screen is also included in pop up
                         i.e. on back navigation from home screen, app is closed.


 */
                    }
                }
            )
        }
        composable(route = Screens.Login_Screen.route) {
            LoginScreen(onClickLoginBtn = {
                navController.navigate(Screens.Home_Screen.route) {
                    popUpTo(Screens.Auth_Screen.route) {
                        inclusive = true
                    }
                }
            })
        }
        composable(
            route = Screens.CategoryProducts_Screen.route,
            arguments = listOf(
                navArgument(name = ScreenArguments.CATEGORY_ID.name) {
                    type = NavType.StringType
                }
            )) { backStackEntry ->

            val categoryId = backStackEntry
                .arguments?.getString(ScreenArguments.CATEGORY_ID.name) ?: return@composable

            //  val category = viewModel.selectedCategory // for entire model


            if (category != null) {
                CategoryProductsPage(
                    categoryId = categoryId, onClickCategory = { id: String ->
                        navController.navigate(Screens.Product_Details_Screen.createRoute(id))
                    },
                    onClickAddToCart = { id: String ->
                        AppUtil.showToast(msg = "id  =  $id", context = context)
                        AppUtil.addItemToCart(productId = id.toString(), context = context)
                    })
            } else {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(White)
                ) {
                    TextComponent(textVal = "Loading...")
                }
            }
        }
        composable(
            route = Screens.Product_Details_Screen.route,
            arguments = listOf(
                navArgument(name = ScreenArguments.PRODUCT_ID.name) {
                    type = NavType.StringType
                }
            )) { backStackEntry ->

            val productId = backStackEntry
                .arguments?.getString(ScreenArguments.PRODUCT_ID.name) ?: return@composable

            //  val category = viewModel.selectedCategory // for entire model

            if (category != null) {
                ProductDetailPage(productId = productId, onClickAddToCart = {
                    AppUtil.addItemToCart(productId = productId, context = context)
                })
            } else {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(White)
                ) {
                    TextComponent(textVal = "Loading...")
                }
            }
        }
        composable(route = Screens.Checkout_Screen.route) {
            CheckoutPage(onClickPay = { amount: Float ->
                AppUtil.startPayment(
                    context = navController.context as Activity,
                    amount = amount
                )
            })
        }
        composable(route = Screens.OrderSuccess_Screen.route) {

            OrderSuccessScreen(
                onClick = {
                    navController.popBackStack()
                    navController.navigate(Screens.Home_Screen.route)
                },
                context = context
            )
        }
        composable(route = Screens.OrdersPage_Screen.route) {

            OrdersPage()
        }
    }
}