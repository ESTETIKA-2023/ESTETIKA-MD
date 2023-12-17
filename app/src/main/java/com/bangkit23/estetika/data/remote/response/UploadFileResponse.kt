package com.bangkit23.estetika.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


data class UploadFileResponse(
    @SerializedName("status") var status: Status? = Status(),
    @SerializedName("data") var data: FileData? = FileData()
)
data class Status (
    @SerializedName("code") var code: Int? = null,
    @SerializedName("message") var message: String? = null
)

@Parcelize
data class FileData (
    @SerializedName("user_id") var userId: String? = null,
    @SerializedName("batik_types_prediction") var batikTypesPrediction: String? = null,
    @SerializedName("batik_confidence") var batikConfidence: Double?= null,
    @SerializedName("firestore_article_data") var firestoreArticleData: List<FirestoreArticleData>? = null,
    @SerializedName("firestore_batik_tourist_attraction_data") var firestoreBatikTouristAttractionData: List<FirestoreBatikTouristAttractionData>?  = null,
    @SerializedName("firestore_batik_shop_recommendation_data") var firestoreBatikShopRecommendationData: List<FirestoreBatikShopRecommendationData>? = null
) : Parcelable

@Parcelize
data class FirestoreArticleData (
    @SerializedName("id") var id: Int? = null,
    @SerializedName("tahun") var tahun: String? = null,
    @SerializedName("image") var image: String? = null,
    @SerializedName("desc_batik") var descBatik: String? = null,
    @SerializedName("document_id") var documentId: String?= null,
    @SerializedName("link_proses_pembuatan") var linkProsesPembuatan: String? = null,
    @SerializedName("asal") var asal: String? = null,
    @SerializedName("arti_motif") var artiMotif: String? = null,
    @SerializedName("teknik_pembuatan") var teknikPembuatan: List<String>? = null,
    @SerializedName("name") var name: String? = null
) : Parcelable

@Parcelize
data class FirestoreBatikTouristAttractionData(
    @SerializedName("id") var id:String? = null,
    @SerializedName("Price") var price: String? = null,
    @SerializedName("Nama Atraksi") var namaAtraksi: String? = null,
    @SerializedName("Province") var province: String? = null,
    @SerializedName("More Info") var moreInfo: String? = null,
    @SerializedName("Image") var image: String? = null,
    @SerializedName("Location") var location: String? = null
) : Parcelable

@Parcelize
data class FirestoreBatikShopRecommendationData (
    @SerializedName("id") var id: String? = null,
    @SerializedName("Gambar") var gambar: String? = null,
    @SerializedName("Nama") var nama: String? = null,
    @SerializedName("Harga") var harga: String? = null,
    @SerializedName("Link") var link: String? = null
) : Parcelable