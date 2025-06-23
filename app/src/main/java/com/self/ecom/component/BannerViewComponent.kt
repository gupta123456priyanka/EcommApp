package com.self.ecom.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.firestore
import com.tbuonomo.viewpagerdotsindicator.compose.DotsIndicator
import com.tbuonomo.viewpagerdotsindicator.compose.model.DotGraphic
import com.tbuonomo.viewpagerdotsindicator.compose.type.ShiftIndicatorType

@Composable
fun BannerViewComponent(modifier: Modifier = Modifier) {
    var bannerList: List<String> by remember { mutableStateOf<List<String>>(emptyList()) }
    LaunchedEffect(Unit) {
        Firebase.firestore.collection("data").document("banners")
            .get().addOnCompleteListener { task: Task<DocumentSnapshot?> ->
                if (task.isSuccessful) {
                    bannerList = task.result?.get("urls") as List<String>
                } else {
                }
            }
    }
    /*  in firestore db we can store data but not image, for images we can use storage that is paid
      so we can host image like google drive , github, cloudinary and just get url from there and store in database.

      in console , add collection , and array of strings in that*/

    val pagerState = rememberPagerState(0) {
        bannerList.size         // total count of pages
    }
    // pageSpacing is for giving spacing between images while sliding
    Column  {
        HorizontalPager(state = pagerState, pageSpacing = 24.dp) { index: Int ->
            ImageComponent(

                url = bannerList.get(index),
                modifier = modifier
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
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BannerViewComponentPrev() {
    BannerViewComponent()
}