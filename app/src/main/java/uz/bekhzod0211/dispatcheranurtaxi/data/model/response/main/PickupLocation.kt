package uz.bekhzod0211.dispatcheranurtaxi.data.model.response.main


import com.google.gson.annotations.SerializedName

data class PickupLocation(
    @SerializedName("address")
    val address: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lng")
    val lng: Double
)