package com.self.ecom

import android.content.Context
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

object AppUtil {

    fun showToast(context: Context, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }

    fun addItemToCart(productId: String, context: Context) {
        // add one more item in db
        val userDoc = Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)
        userDoc.get().addOnCompleteListener {
            if (it.isSuccessful) {
                val currentCart = it.result.get("cartItems") as? Map<String, Long> ?: emptyMap()
                val currentQuantity = currentCart[productId] ?: 0
                val updatedQuantity = currentQuantity + 1
                val updatedCart = mapOf("cartItems.$productId" to updatedQuantity)
                userDoc.update(updatedCart).addOnCompleteListener {
                    if (it.isSuccessful) {
                        showToast(context = context, msg = "Success add to cart")
                    } else {
                        showToast(context = context, msg = "Failure add to cart")
                    }
                }
            }
        }
    }
}