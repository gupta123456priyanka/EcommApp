package com.self.ecom.model

data class UserModel(
    val name: String = "",
    val email: String = "",
    val uid: String = "",
    val cartItems: Map<String, Long> = emptyMap () ,  // for id of product, quantity,
    val address : String = ""
)
