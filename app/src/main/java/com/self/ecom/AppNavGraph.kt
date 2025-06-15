package com.self.ecom

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.self.ecom.screens.AuthScreen
import com.self.ecom.screens.HomeScreen
import com.self.ecom.screens.LoginScreen
import com.self.ecom.screens.SignupScreen

@Composable
fun AppNavGraph(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.Auth_Screen.route,
        modifier = modifier
    ) {
        composable(route = Screens.Auth_Screen.route) {
            AuthScreen(
                onClickSignup = {
                    navController.navigate(route = Screens.Signup_Screen.route)
                },
                onClickLogin = { navController.navigate(route = Screens.Login_Screen.route) })
        }
        composable(route = Screens.Home_Screen.route) {
            HomeScreen()
        }
        composable(route = Screens.Signup_Screen.route) {
            SignupScreen(onClickSignupBtn = {
//                navController.navigate()
            })
        }
        composable(route = Screens.Login_Screen.route) {
            LoginScreen(onClickLoginBtn = {
                navController.navigate(Screens.Home_Screen.route)
            })
        }
    }
}