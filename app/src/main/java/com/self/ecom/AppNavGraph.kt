package com.self.ecom

import android.R.id.home
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
                navController.navigate(Screens.Home_Screen.route)
            })
        }
    }
}