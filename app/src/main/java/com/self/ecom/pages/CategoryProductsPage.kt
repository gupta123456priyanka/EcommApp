package com.self.ecom.pages

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore
import com.self.ecom.AppUtil
import com.self.ecom.component.IconButtonComposable
import com.self.ecom.component.ImageComponent
import com.self.ecom.component.SpacerComponent
import com.self.ecom.component.TextComponentMoreParams
import com.self.ecom.model.ProductModel
import com.self.ecom.ui.theme.White
import com.self.ecom.viewmodel.CategoryViewModel

@Composable
fun CategoryProductsPage(modifier: Modifier = Modifier, categoryId: String?,
                         onClickCategory: (String) -> Unit) {
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
            .whereEqualTo(
                "category",
                categoryId
            ) // to filter products with category, not show all products
            .get()
            .addOnCompleteListener { task: Task<QuerySnapshot?> ->
                if (task.isSuccessful && task.result?.documents != null) {
                    val list = task.result?.documents?.mapNotNull { doc: DocumentSnapshot? ->
                        doc?.toObject(ProductModel::class.java)
                    }!!
                    productList = list
//                        .plus(list).plus(list).plus(list).plus(list)// for testing
//                    viewModel.setAllCategories(productList)
                } else {
                    AppUtil.showToast(msg = "products is null", context = context)
                }
            }
    }
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(White)
    ) {
        items(productList.chunked(2)) { rowItems ->
            Row {
                rowItems.forEach { item ->
                    ProductItem(
                        item, modifier = Modifier
                            .weight(1f)
                            .padding(8.dp),
                        onClickCategory = onClickCategory
                    )
                }
                if (rowItems.size == 1) { // added another empty composable for only 1 item in row
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun ProductItem(
    productModel: ProductModel, modifier: Modifier = Modifier,
    onClickCategory: (String) -> Unit
) {

    Card(
        modifier
            .clickable(onClick = {
//                viewModel.selectedCategory.value = categoryModel // if we want to sen whole model
                onClickCategory(productModel.id) // if we want to send only few properties of model
            }),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(contentColor = MaterialTheme.colorScheme.primary)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight()
                .padding(8.dp)
        ) {
            SpacerComponent(heightVal = 10.dp)
            ImageComponent(
                url = productModel.images.firstOrNull(),
                modifier = Modifier.height(120.dp)
            )
            SpacerComponent(heightVal = 10.dp)
            TextComponentMoreParams(
                textVal = productModel.title,
                fontSizeVal = 18.sp,
                color = White,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier.padding(8.dp)
            )
            Row {
                TextComponentMoreParams(
                    textVal = "$" + productModel.price,
                    fontSizeVal = 10.sp,
                    color = White,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Normal,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.padding(8.dp),
                    style = TextStyle(textDecoration = TextDecoration.LineThrough) // truncated text
                )
                Spacer(modifier = Modifier.width(2.dp))
                TextComponentMoreParams(
                    textVal = "$" + productModel.actualPrice,
                    fontSizeVal = 10.sp,
                    color = White,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.padding(8.dp)
                )
                Spacer(Modifier.weight(1f)) // cart icon will be always on extreme right, this fills up all the space
                IconButtonComposable(onClick = {}, imageVector = Icons.Filled.ShoppingCart)
            }
        }
    }
}