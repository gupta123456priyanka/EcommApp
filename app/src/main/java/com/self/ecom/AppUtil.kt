package com.self.ecom

import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import com.razorpay.Checkout
import com.self.ecom.model.OrderModel
import org.json.JSONObject
import java.util.UUID

object AppUtil {

    fun showToast(context: Context, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
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

    fun removeItemFromCart(productId: String, context: Context, removeAll: Boolean = false) {
        // add one more item in db
        val userDoc = Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)
        userDoc.get().addOnCompleteListener {
            if (it.isSuccessful) {
                val currentCart = it.result.get("cartItems") as? Map<String, Long> ?: emptyMap()
                val currentQuantity = currentCart[productId] ?: 0
                val updatedQuantity = currentQuantity - 1
                val updatedCart = if (updatedQuantity <= 0 || removeAll) {
                    mapOf("cartItems.$productId" to FieldValue.delete())
                } else {
                    mapOf("cartItems.$productId" to updatedQuantity)
                }
                userDoc.update(updatedCart).addOnCompleteListener {
                    if (it.isSuccessful) {
                        showToast(context = context, msg = "Success remove from cart")
                    } else {
                        showToast(context = context, msg = "Failure remove from cart")
                    }
                }
            }
        }
    }

    fun getDiscountPercentage(): Float {
        return 15.0f
    }

    fun getTaxPercentage(): Float {
        return 28.0f
    }

    fun getRazorPayApiKey(): String {
        return "rzp_test_JyO8V8EFtLPhBJ"
    }

    fun startPayment(amount: Float, context: Activity) {
        val checkout = Checkout()
        checkout.setKeyID(getRazorPayApiKey())
        val options = JSONObject()
        options.put("name", "Ecom App")
        options.put("description", "desc")
        options.put("amount", amount * 100) // convert dollar to rupees
        options.put("currency", "USD")
        checkout.open(context, options)
    }

    fun clearCartAndAddToOrders() {
        val userDoc = Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)
        userDoc.get().addOnCompleteListener {
            if (it.isSuccessful) {
                val currentCart = it.result.get("cartItems") as? Map<String, Long> ?: emptyMap()
                val order = OrderModel(
                    id = "ORD_"+UUID.randomUUID().toString().replace("-", "").take(10).uppercase(), // take 10 digits only
                    userId = FirebaseAuth.getInstance().currentUser?.uid!!,
                    date = Timestamp.now(),
                    items = currentCart,
                    status = "ORDERED",
                    address = it.result.get("address") as String
                )
                // push order object to firebase
                Firebase.firestore.collection("orders").document(order.id).set(order)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            userDoc.update("cartItems", FieldValue.delete())
                        }
                    }
// here the order item will be created , then cart items will be deleted

            }
        }
    }
}