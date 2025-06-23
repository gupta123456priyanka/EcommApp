package com.self.ecom.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore
import com.self.ecom.AppUtil
import com.self.ecom.component.BannerViewComponent
import com.self.ecom.component.HeaderViewComponent
import com.self.ecom.component.ImageComponent
import com.self.ecom.component.SpacerComponent
import com.self.ecom.component.TextComponent
import com.self.ecom.component.TextComponentH3Black
import com.self.ecom.ui.theme.White

@Composable
fun HomePage(modifier: Modifier = Modifier, onClickCategory: (CategoryModel) -> Unit) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        HeaderViewComponent(modifier)
        SpacerComponent(heightVal = 10.dp)
        BannerViewComponent(
            Modifier
                .fillMaxWidth()
                .height(150.dp)
        )
        SpacerComponent(heightVal = 10.dp)
        TextComponentH3Black(textVal = "Categories")
        CategoriesView(
            modifier
                .fillMaxWidth()
                .height(150.dp),
            onClickCategory = onClickCategory
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomePagePr() {
    HomePage(onClickCategory = {})
}

@Composable
fun CategoriesView(modifier: Modifier = Modifier, onClickCategory: (CategoryModel) -> Unit) {
    var categoryList: List<CategoryModel> by remember {
        mutableStateOf<List<CategoryModel>>(
            emptyList()
        )
    }

    val context = LocalContext.current
    // create another data in firebase db named stock
    LaunchedEffect(Unit) {

        Firebase.firestore.collection("data")
            .document("stock").collection("categories").get()
            .addOnCompleteListener { task: Task<QuerySnapshot?> ->
                if (task.isSuccessful && task.result?.documents != null) {
                    categoryList = task.result?.documents?.mapNotNull { doc: DocumentSnapshot? ->
                        doc?.toObject(CategoryModel::class.java)
                    }!!
                } else {
                    AppUtil.showToast(msg = "categories is nul", context = context)
                }
            }
    }

    // lazy row is used instead of pager bcoz we need name to be shown with image
    LazyRow(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
        items(categoryList) { item ->
            CategoryItem(item, onClickCategory = onClickCategory)
        }
    }
    /*
    Column {
        val pagerState = rememberPagerState(initialPage = 0) {
            categoryList.size
        }
        HorizontalPager(state = pagerState, pageSpacing = 24.dp) { index: Int ->
            ImageComponent(
                url = categoryList.get(index).imageUrl, modifier = modifier
                    .clip(
                        RoundedCornerShape(16.dp)
                    )
            )
        }
        SpacerComponent(heightVal = 10.dp)
        DotsIndicator(
            pagerState = pagerState,
            dotCount = categoryList.size,
            modifier = Modifier,
            type = ShiftIndicatorType(
                dotsGraphic = DotGraphic(
                    color = MaterialTheme.colorScheme.primary,
                    size = 10.dp
                )
            )
        )
    }*/
}

@Composable
fun CategoryItem(categoryModel: CategoryModel, onClickCategory: (CategoryModel) -> Unit) {

    Card(
        Modifier
            .size(110.dp)
            .clickable(onClick = { onClickCategory(categoryModel) }),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(contentColor = MaterialTheme.colorScheme.primary)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxHeight()
        ) {
            SpacerComponent(heightVal = 10.dp)
            ImageComponent(
                url = categoryModel.imageUrl,
                modifier = Modifier.size(50.dp)
            )
            SpacerComponent(heightVal = 10.dp)
            TextComponent(
                textVal = categoryModel.name,
                fontSizeVal = 7.sp,
                color = White,
                textAlign = TextAlign.Center
            )
        }
    }
}

data class CategoryModel(val id: String = "", val name: String = "", val imageUrl: String = "")