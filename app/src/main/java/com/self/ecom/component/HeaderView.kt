package com.self.ecom.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.firestore
import com.self.ecom.ui.theme.Pink40

@Composable
fun HeaderViewComponent(modifier: Modifier = Modifier) {
    var name by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {  // Unit means it is called once
        val documentPath: String? = FirebaseAuth.getInstance().currentUser?.uid
        documentPath?.let {
            Firebase.firestore.collection("users").document(it)
                .get().addOnCompleteListener { task: Task<DocumentSnapshot?> ->
                    val fullName = task.result?.get("name").toString()
//                    uid = task.result?.get("uid").toString()
//                    email = task.result?.get("email").toString()
                    name = fullName.split(" ").get(0)   // first name
                }
        }
    }

    Row(
        Modifier.fillMaxWidth().padding(16.dp) ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            TextComponentH1Black(textVal = "Welcome")
            TextComponentH3Black(
                textVal = "User name $name",
                modifier = Modifier.background(Pink40)
            )
        }
        Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HeaderViewPRe() {
    HeaderViewComponent()
}