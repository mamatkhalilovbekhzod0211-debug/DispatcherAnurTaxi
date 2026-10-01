package uz.bekhzod0211.dispatcheranurtaxi.data.model.response.main


import com.google.gson.annotations.SerializedName

data class OrderCreateResponse(
    @SerializedName("broadcasted_to")
    val broadcastedTo: Int,
    @SerializedName("ride")
    val ride: Ride
)