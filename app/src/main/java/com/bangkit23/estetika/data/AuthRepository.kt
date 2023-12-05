package com.bangkit23.estetika.data

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepository @Inject constructor(private val auth: FirebaseAuth) {

    suspend fun login(email: String, password: String, onComplete: (Boolean) -> Unit) = withContext(Dispatchers.IO) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                onComplete.invoke(true)
            } else {
                onComplete.invoke(false)
            }
        }.await()
    }

    suspend fun register(email: String, password: String, onComplete: (Boolean) -> Unit) = withContext(Dispatchers.IO) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                onComplete.invoke(true)
            } else {
                onComplete.invoke(false)
            }
        }.await()
    }

    fun logout() = auth.signOut()

    fun currentUser() = auth.currentUser

    fun signOut() = Firebase.auth.signOut()

    fun hasUser():Boolean = auth.currentUser != null
}