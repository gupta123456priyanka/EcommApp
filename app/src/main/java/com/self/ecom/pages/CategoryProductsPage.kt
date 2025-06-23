package com.self.ecom.pages

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore
import com.self.ecom.AppUtil
import com.self.ecom.component.SpacerComponent
import com.self.ecom.component.TextComponentH2Black
import com.self.ecom.model.ProductModel
import com.self.ecom.ui.theme.Pink40
import com.self.ecom.ui.theme.White
import com.self.ecom.viewmodel.CategoryViewModel

@Composable
fun CategoryProductsPage(modifier: Modifier = Modifier, categoryId: String?) {
    val c = LocalContext.current
    val viewModel: CategoryViewModel = viewModel(c as ComponentActivity)

    var productList: List<ProductModel> by remember {
        mutableStateOf<List<ProductModel>>(
            emptyList()
        )
    }

    val context = LocalContext.current
    // create another data in firebase db named stock
    LaunchedEffect(Unit) {

        Firebase.firestore.collection("data")
            .document("stock").collection("products")
            .whereEqualTo("category", categoryId) // to filter products with category, not show all products
            .get()
            .addOnCompleteListener { task: Task<QuerySnapshot?> ->
                if (task.isSuccessful && task.result?.documents != null) {
                    productList = task.result?.documents?.mapNotNull { doc: DocumentSnapshot? ->
                        doc?.toObject(ProductModel::class.java)
                    }!!
//                    viewModel.setAllCategories(productList)
                } else {
                    AppUtil.showToast(msg = "products is null", context = context)
                }
            }
    }
    LazyColumn (verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        items(productList) { item ->

            Box(Modifier
                .fillMaxSize()
                .background(White)) {

                Column {

                    TextComponentH2Black(textVal = " ${item.title}")
                    SpacerComponent(heightVal = 20.dp, modifier = Modifier.background(Pink40))
                }
            }
//            ProductItem(item)
        }
    }

}

@Composable
fun ProductItem(productModel: ProductModel ) {

}