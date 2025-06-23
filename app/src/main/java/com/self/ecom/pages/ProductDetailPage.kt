package com.self.ecom.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.self.ecom.component.ButtonComponent
import com.self.ecom.component.IconButtonComposable
import com.self.ecom.component.ImageComponent
import com.self.ecom.component.SpacerComponent
import com.self.ecom.component.TextComponent
import com.self.ecom.component.TextComponentMoreParams
import com.self.ecom.model.ProductModel
import com.self.ecom.ui.theme.Black
import com.self.ecom.ui.theme.White
import com.tbuonomo.viewpagerdotsindicator.compose.DotsIndicator
import com.tbuonomo.viewpagerdotsindicator.compose.model.DotGraphic
import com.tbuonomo.viewpagerdotsindicator.compose.type.ShiftIndicatorType

@Composable
fun ProductDetailPage(
    modifier: Modifier = Modifier, productId: String?,
    onClickAddToCart: () -> Unit,
) {
    var bannerList: List<String> by remember { mutableStateOf<List<String>>(emptyList()) }

    var product by remember { mutableStateOf(ProductModel()) }
    val pagerState = rememberPagerState(0) {
        bannerList.size         // total count of pages
    }
    LaunchedEffect(Unit) {

        Firebase.firestore.collection("data")
            .document("stock").collection("products")
            .document(productId.toString())
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    var result = it.result.toObject(ProductModel::class.java)
                    if (result != null) {
                        product = result
                        bannerList = result.images
                    }
                }

            }
    }
    Box(
        Modifier
            .fillMaxSize()
            .background(White)
    ) {
//        TextComponent(textVal = "ProductDetailPage ${product.toString()}")
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(
                Modifier
                    .height(50.dp)
                    .fillMaxWidth()
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                HorizontalPager(state = pagerState, pageSpacing = 24.dp) { index: Int ->
                    ImageComponent(

                        url = bannerList.get(index),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .clip(
                                RoundedCornerShape(16.dp)
                            )
                    )
                }
                SpacerComponent(heightVal = 10.dp)
                DotsIndicator(
                    pagerState = pagerState,
                    dotCount = bannerList.size,
                    modifier = Modifier,
                    type = ShiftIndicatorType(
                        dotsGraphic = DotGraphic(
                            color = MaterialTheme.colorScheme.primary,
                            size = 10.dp
                        )
                    )
                )
                Row(Modifier.padding(16.dp)) {
                    TextComponentMoreParams(
                        textVal = "$" + product.price,
                        fontSizeVal = 14.sp,
                        color = Black,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Normal,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        modifier = Modifier.padding(8.dp),
                        style = TextStyle(textDecoration = TextDecoration.LineThrough) // truncated text
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    TextComponentMoreParams(
                        textVal = "$" + product.actualPrice,
                        fontSizeVal = 16.sp,
                        color = Black,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        modifier = Modifier.padding(8.dp)
                    )
                    Spacer(Modifier.weight(1f)) // cart icon will be always on extreme right, this fills up all the space
                    IconButtonComposable(onClick = {}, imageVector = Icons.Filled.Favorite)
                }
                ButtonComponent(
                    textVal = "Add to Cart",
                    isFilled = true,
                    onClick = onClickAddToCart,
                    isEnabled = true
                )

                Spacer(
                    Modifier
                        .height(8.dp)
                        .fillMaxWidth()
                )
                TextComponent(
                    textVal = "Product description :",
                    fontSizeVal = 16.sp,
                    modifier = Modifier.wrapContentWidth(),
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start
                )
                Spacer(
                    Modifier
                        .height(8.dp)
                        .fillMaxWidth()
                )
                TextComponent(
                    textVal = product.description,
                    fontSizeVal = 16.sp,
                    textAlign = TextAlign.Justify,
                )
            }
        }
    }
}