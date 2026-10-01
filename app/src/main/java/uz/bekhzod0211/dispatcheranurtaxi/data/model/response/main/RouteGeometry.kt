package uz.bekhzod0211.dispatcheranurtaxi.data.model.response.main


import com.google.gson.annotations.SerializedName

data class RouteGeometry(
    @SerializedName("coordinates")
    val coordinates: List<List<Double>>,
    @SerializedName("type")
    val type: String
)