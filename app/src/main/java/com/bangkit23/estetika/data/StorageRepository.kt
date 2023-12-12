package com.bangkit23.estetika.data

import com.bangkit23.estetika.data.model.BatikArticle
import com.bangkit23.estetika.data.model.BatikTourism
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class StorageRepository @Inject constructor(private val db: FirebaseFirestore) {

    fun getBatikTourism(): Flow<Resources<List<BatikTourism>>> = callbackFlow {
        var snapshotStateListener: ListenerRegistration? = null

        try {
            snapshotStateListener = db.collection(TOURISM_REFS).orderBy("Nama Atraksi", Query.Direction.DESCENDING)
                .addSnapshotListener { snapshot, e ->
                    val response = if (snapshot != null) {
                        val terms = snapshot.toObjects(BatikTourism::class.java)
                        Resources.Success(data = terms)
                    } else {
                        Resources.Error(throwable = e?.cause)
                    }
                    trySend(response)
                }

        } catch (e: Exception) {
            trySend(Resources.Error(e.cause))
            e.printStackTrace()
        }

        awaitClose {
            snapshotStateListener?.remove()
        }
    }

    fun getBatikArticle(): Flow<Resources<List<BatikArticle>>> = callbackFlow {
        var snapshotStateListener: ListenerRegistration? = null

        try {
            snapshotStateListener = db.collection(ARTICLE_REFS).orderBy("id", Query.Direction.ASCENDING)
                .addSnapshotListener { snapshot, e ->
                    val response = if (snapshot != null) {
                        val terms = snapshot.toObjects(BatikArticle::class.java)
                        Resources.Success(data = terms)
                    } else {
                        Resources.Error(throwable = e?.cause)
                    }
                    trySend(response)
                }

        } catch (e: Exception) {
            trySend(Resources.Error(e.cause))
            e.printStackTrace()
        }

        awaitClose {
            snapshotStateListener?.remove()
        }
    }

    companion object {
        private const val TOURISM_REFS = "batik_tourist_attractions"
        private const val ARTICLE_REFS = "batik_article_collection"
    }
}

sealed class Resources<T>(
    val data: T? = null,
    val throwable: Throwable? = null,
) {
    class Loading<T> : Resources<T>()
    class Success<T>(data: T?) : Resources<T>(data = data)
    class Error<T>(throwable: Throwable?) : Resources<T>(throwable = throwable)

}