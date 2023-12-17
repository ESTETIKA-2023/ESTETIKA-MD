package com.bangkit23.estetika.data.model

import android.os.Parcelable
import com.google.firebase.firestore.PropertyName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BatikArticle(
    @get:PropertyName("arti_motif") @set:PropertyName("arti_motif") var arti_motif: String? = null,
    @get:PropertyName("asal") @set:PropertyName("asal") var asal: String? = null,
    @get:PropertyName("desc_batik") @set:PropertyName("desc_batik") var desc_batik: String? = null,
    @get:PropertyName("document_id") @set:PropertyName("document_id") var document_id: String? = null,
    @get:PropertyName("id") @set:PropertyName("id") var id: Int? = null,
    @get:PropertyName("image") @set:PropertyName("image") var image: String? = null,
    @get:PropertyName("link_proses_pembuatan") @set:PropertyName("link_proses_pembuatan") var link_proses_pembuatan: String? = null,
    @get:PropertyName("name") @set:PropertyName("name") var name: String? = null,
    @get:PropertyName("teknik_pembuatan") @set:PropertyName("teknik_pembuatan") var teknik_pembuatan: List<String>? = null
) : Parcelable
