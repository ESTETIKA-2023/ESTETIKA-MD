package com.bangkit23.estetika.data.model

import com.google.firebase.firestore.PropertyName

data class BatikTourism(
    @get:PropertyName("Image") @set:PropertyName("Image") var image: String? = null,
    @get:PropertyName("Location") @set:PropertyName("Location") var location: String? = null,
    @get:PropertyName("More Info") @set:PropertyName("More Info") var moreInfo: String? = null,
    @get:PropertyName("Nama Atraksi") @set:PropertyName("Nama Atraksi") var name: String? = null,
    @get:PropertyName("Price") @set:PropertyName("Price") var price: String? = null,
    @get:PropertyName("Province") @set:PropertyName("Province") var province: String? = null
) {
    constructor() : this(null, null, null, null, null, null)
}
