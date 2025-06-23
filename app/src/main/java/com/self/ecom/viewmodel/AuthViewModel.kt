package com.self.ecom.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.self.ecom.AppUtil
import com.self.ecom.model.UserModel

class AuthViewModel : ViewModel() {
    private val auth = Firebase.auth
    /*  - auth is required for login & signup, it will create account using email pwd
      - we will store it in firestore
     -  console>Firestore database >create database> any location> start in test mode> create


     */

    private val firestore =
        Firebase.firestore   // for dependency , tools>Firebase>Cloud firestore> add sdk


    fun signup(
        email: String,
        name: String,
        password: String,
        onResult: (Boolean, String?) -> Unit,
        context: Context
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task: Task<AuthResult?> ->
                if (task.isSuccessful) {
                    val userId =
                        task.result?.user?.uid // whenever account is created it has a unique id called uid
                    // with this id we can uniquely identify
                    // store it in database of firestore
                    val userModel = UserModel(name = name, email = email, uid = userId!!)
                    // now add this usermodel data in the firestore database as a document
                    firestore.collection("users").document(userId).set(userModel)
                        .addOnCompleteListener { dbTask: Task<Void?> -> // addOnCompleteListener is used to knw if task completed or not  with success failure
                            if (dbTask.isSuccessful) {
                                onResult(true, null)
                            } else {
                                onResult(
                                    true,
                                    "Something went wrong while storing data in datbase"
                                )
                            }
                        }
                } else {
                    onResult(false, task.exception?.localizedMessage.toString())
                }
            }
    }

    fun login(
        email: String,
        password: String,
        onResult: (Boolean, String?) -> Unit,
        context: Context
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task: Task<AuthResult?> ->
                if (task.isSuccessful) {
                    onResult(true, null)
                } else {
                    onResult(false, task.exception?.localizedMessage ?: "Error")
                }
            }
    }
}
