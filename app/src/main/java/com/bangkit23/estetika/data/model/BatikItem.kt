package com.bangkit23.estetika.data.model

import com.google.firebase.firestore.PropertyName

data class BatikItem (
    @get:PropertyName("Gambar") @set:PropertyName("Gambar") var gambar: String? = null,
    @get:PropertyName("Harga") @set:PropertyName("Harga") var harga: String? = null,
    @get:PropertyName("Link") @set:PropertyName("Link") var link: String? = null,
    @get:PropertyName("Nama") @set:PropertyName("Nama") var nama: String? = null
)
{
    constructor(): this(null, null, null, null)
}