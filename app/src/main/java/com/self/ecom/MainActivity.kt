package com.self.ecom

import android.R.attr.onClick
import android.app.AlertDialog
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.razorpay.PaymentResultListener
import com.self.ecom.ui.theme.EcommAppTheme

//import dagger.hilt.android.AndroidEntryPoint


class MainActivity : ComponentActivity(), PaymentResultListener {
    lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EcommAppTheme {
                navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->



                    AppNavGraph(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController
                    )
                }
            }
        }
    }


    override fun onPaymentSuccess(p0: String?) {
//        navController.navigate(Screens.OrderSuccess_Screen.route)
        AppUtil.clearCartAndAddToOrders()
        // check in firestore that order is created and cartitem is removed

//        AppUtil.showToast(msg = "Payment Success, $p0", context =this)

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Payment successful")
        builder.setMessage("Thank you, Your payment was completed successfully and your order has been placed")
        builder.setPositiveButton("OK") { _, _ ->

            navController.popBackStack()
            navController.navigate(Screens.Home_Screen.route)
        }.setCancelable(false).show()
    }

    override fun onPaymentError(p0: Int, p1: String?) {

        AppUtil.showToast(msg = "Payment Failed $p1", context = this)
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EcommAppTheme {
        Greeting("Android")
    }
}