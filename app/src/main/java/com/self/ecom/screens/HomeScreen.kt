package com.self.ecom.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.self.ecom.AppUtil
import com.self.ecom.Constants.BG_COLOR
import com.self.ecom.component.ButtonComponent
import com.self.ecom.component.TextComponent
import com.self.ecom.component.TextComponentH3White
import com.self.ecom.model.CategoryModel
import com.self.ecom.pages.CartPage
import com.self.ecom.pages.FavouritePage
import com.self.ecom.pages.HomePage
import com.self.ecom.pages.ProfilePage
import com.self.ecom.ui.theme.Blue30
import com.self.ecom.ui.theme.Pink40
import com.self.ecom.ui.theme.Red

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier, logoutClick: () -> Unit,
    onClickCategory: (CategoryModel) -> Unit,
    onClickAddToCart: (String) -> Unit,
    onClickRemoveFromCart: (String) -> Unit,
    onClickRemoveAllCart: (String) -> Unit,
    onClickCheckout: () -> Unit
) {
    var selectedIndex by remember { mutableStateOf(0) }
    var context = LocalContext.current
    Scaffold(modifier = Modifier.background(Pink40).fillMaxSize(),bottomBar = {
        NavigationBar(
            containerColor = Red,
            tonalElevation = 0.dp,
            modifier = Modifier.fillMaxWidth().height(100.dp)
        ) {
            listOfNavItems().forEachIndexed { index, item ->
                NavigationBarItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Blue30),
                    selected = index == selectedIndex,
                    onClick = {
                        selectedIndex = index
                        AppUtil.showToast(msg = "index $index", context = context)
                    },
                    icon = { Icon(imageVector = item.icon, contentDescription = item.label) },
                    label = { TextComponentH3White(textVal = item.label) }
                )
            }
        }

    }) { innerPadding ->
        ContentScreen(
            logoutClick = logoutClick,
            modifier = Modifier.padding(innerPadding)
                .background(BG_COLOR)
            ,
            selectedIndex = selectedIndex,
            onClickCategory = onClickCategory,
            onClickRemoveFromCart = onClickRemoveFromCart,
            onClickAddToCart = onClickAddToCart,
            onClickRemoveAllCart = onClickRemoveAllCart,
            onClickCheckout = onClickCheckout
        )
    }
}

fun listOfNavItems() = listOf<NavItems>(
    NavItems(icon = Icons.Default.Home, label = "Home"),
    NavItems(icon = Icons.Default.Favorite, label = "Favorite"),
    NavItems(icon = Icons.Default.ShoppingCart, label = "Cart"),
    NavItems(icon = Icons.Default.Person, label = "Profile")
)

data class NavItems(val icon: ImageVector, val label: String)

@Composable
fun ContentScreen(
    modifier: Modifier = Modifier, logoutClick: () -> Unit, selectedIndex: Int,
    onClickCategory: (CategoryModel) -> Unit,
    onClickAddToCart: (String) -> Unit,
    onClickRemoveFromCart: (String) -> Unit,
    onClickRemoveAllCart: (String) -> Unit,
    onClickCheckout: () -> Unit
) {
    Column(
        modifier
            .fillMaxSize()
//            .background(BG_COLOR)
    ) {
        TextComponent(textVal = "HomeScreen")
        ButtonComponent(
            textVal = "Logout", isEnabled = true, isFilled = true, onClick = logoutClick
        )
        when (selectedIndex) {
            0 -> HomePage(modifier, onClickCategory = onClickCategory)
            1 -> FavouritePage(modifier)
            2 -> CartPage(
                modifier,
                onClickAddToCart = onClickAddToCart,
                onClickRemoveFromCart = onClickRemoveFromCart,
                onClickRemoveAllCart = onClickRemoveAllCart,
                onClickCheckout = onClickCheckout
            )

            3 -> ProfilePage(modifier)
        }

    }
}